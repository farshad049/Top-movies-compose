package com.farshad.topmovies_compose.ui.screnns.submitMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.repository.SubmitMovieRepository
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class SubmitMultipartViewModel @Inject constructor(
    private val repository: SubmitMovieRepository
):ViewModel() {

    private val _submitMovieMultipartFlow = Channel<SubmitResponseModel>()
    val submitMovieMultipartFlow = _submitMovieMultipartFlow.receiveAsFlow()



    private val _validationMutableFlow= MutableStateFlow<SubmitFieldValidationModel>(
        SubmitFieldValidationModel()
    )
    val validationFlow = _validationMutableFlow.asStateFlow()



    fun pushMovieMultipart(
        poster: MultipartBody.Part?,
        title: String,
        imdb_id: String,
        country: String,
        year: String
    ){
        viewModelScope.launch {
            val response=repository.pushMovieMultipart(poster,title,imdb_id,country,year)
            _submitMovieMultipartFlow.send(response)
        }
    }




    fun validateMultipart(
        title : String,
        imdb_id : String,
        country : String,
        year : String ,
        poster : MultipartBody.Part?
    ){

        val titleB = title.trim()
        val imdbIdB = imdb_id.trim()
        val countryB = country.trim()
        val yearB = year.trim()

        when{
            titleB.isEmpty() -> {
                _validationMutableFlow.value=
                    SubmitFieldValidationModel(title = "* please enter a valid title")
                return
            }
            imdbIdB.isEmpty() -> {
                _validationMutableFlow.value=
                    SubmitFieldValidationModel(imdbId = "* please enter a valid IMDB ID")
                return
            }
            countryB.isEmpty() -> {
                _validationMutableFlow.value =
                    SubmitFieldValidationModel(country = "* please enter a valid country name")
                return
            }
            yearB.isEmpty() && year.length < 4 -> {
                _validationMutableFlow.value=
                    SubmitFieldValidationModel(year = "* please enter a valid year")
                return

            }else ->{
            _validationMutableFlow.value = SubmitFieldValidationModel(
                title = null,
                imdbId = null,
                country = null,
                year  = null,
                poster  = null,
            )


            pushMovieMultipart(
                    title = titleB,
                    imdb_id = imdbIdB ,
                    country = countryB ,
                    year = yearB,
                    poster = poster
            )

          }
        }

    }





}