package com.farshad.topmovies_compose.data.model.network

data class PagingModel(
    val data: List<NetworkMovieModel> = listOf(),
    val metadata: Metadata = Metadata()
)