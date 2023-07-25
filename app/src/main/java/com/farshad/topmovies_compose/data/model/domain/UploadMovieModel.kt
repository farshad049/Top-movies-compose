package com.farshad.topmovies_compose.data.model.domain

import okhttp3.MultipartBody

data class UploadMovieModel(
    var country: String = "",
    val director: String = "",
    val id: Int = 0,
    var imdb_id: String = "",
    val imdb_rating: String = "",
    val imdb_votes: String = "",
    val poster: String? = "",
    var title: String = "",
    var year: Int = 0
)

data class UploadMovieMultipart(
    var country: String = "",
    val director: String = "",
    val id: Int = 0,
    var imdb_id: String = "",
    val imdb_rating: String = "",
    val imdb_votes: String = "",
    val poster: MultipartBody.Part?,
    var title: String = "",
    var year: Int = 0,

    )