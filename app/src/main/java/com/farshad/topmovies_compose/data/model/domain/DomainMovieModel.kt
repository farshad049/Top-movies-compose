package com.farshad.topmovies_compose.data.model.domain

data class DomainMovieModel(
val actors: String = "",
val country: String = "",
val director: String = "",
val genres: List<String> = listOf(),
val id: Int = 0,
val images: List<String> = listOf(),
val imdb_rating: String = "",
val plot: String = "",
val poster: String = "",
val rated: String = "",
val title: String = "",
val year: String = "",
val runTime: String = "",
val imdb_votes: String= "",
val released: String= "",
val writer: String= "",
val awards: String ="",
val metaScore: String= ""
)

