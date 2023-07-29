package com.farshad.topmovies_compose.data.repository

import com.farshad.topmovies_compose.data.model.domain.UploadMovieModel
import com.farshad.topmovies_compose.data.remote.ApiClient
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitResponseModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class SubmitMovieRepository @Inject constructor(
    private val apiClient: ApiClient,
    ){

    suspend fun pushMovieBase64(movie: UploadMovieModel): SubmitResponseModel {
        val response= apiClient.pushMovieBase64(movie)

        return when{
            response.isSuccessful ->{
                SubmitResponseModel.Success(response.body)
            }
            !response.isSuccessful -> {
                val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("errors") }
                SubmitResponseModel.Error(jsonObj ?: "Something went wrong")
            }
            else -> {
                SubmitResponseModel.Loading
            }
        }

    }



    suspend fun pushMovieMultipart(
        poster : MultipartBody.Part?,
        title : String,
        imdbId : String,
        country : String,
        year : String ,
    ): SubmitResponseModel {

        val titleBody : RequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val imdbIdBody : RequestBody = imdbId.toRequestBody("text/plain".toMediaTypeOrNull())
        val yearBody : RequestBody = year.toRequestBody("text/plain".toMediaTypeOrNull())
        val countryBody : RequestBody = country.toRequestBody("text/plain".toMediaTypeOrNull())

        val response= apiClient.pushMovieMulti(poster,titleBody,imdbIdBody,countryBody,yearBody)


        return when{
            !response.isSuccessful -> {
                val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("errors") }
                SubmitResponseModel.Error(jsonObj ?: "Something went wrong")
            }
            response.isSuccessful -> {
                SubmitResponseModel.Success(response.body)
            }
            else -> {
                SubmitResponseModel.Loading
            }
        }
    }















}