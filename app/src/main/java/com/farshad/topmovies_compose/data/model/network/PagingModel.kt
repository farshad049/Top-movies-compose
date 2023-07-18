package com.farshad.moviesAppCompose.data.model.network

data class PagingModel(
    val data: List<NetworkMovieModel> = listOf(),
    val metadata: Metadata = Metadata()
)