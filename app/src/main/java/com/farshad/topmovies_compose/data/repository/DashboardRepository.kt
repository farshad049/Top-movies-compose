package com.farshad.moviesAppCompose.data.repository

import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.data.model.mapper.MovieMapper
import com.farshad.moviesAppCompose.data.model.network.GenresModel
import com.farshad.topmovies_compose.data.remote.ApiClient
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
){


    suspend fun getMovieByPage(pageNumber: Int):List<DomainMovieModel>{
        val response= apiClient.getMoviesPaging(pageNumber)
        if (!response.isSuccessful){
            return emptyList()
        }
        return response.body.data.map { movieMapper.buildFrom(it) }
    }



    suspend fun getAllGenres():List<GenresModel>{
        val response= apiClient.getAllGenres()
        if (!response.isSuccessful){
            return emptyList()
        }
        return response.body
    }







}