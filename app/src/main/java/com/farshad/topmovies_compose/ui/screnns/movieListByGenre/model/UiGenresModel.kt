package com.farshad.topmovies_compose.ui.screnns.movieListByGenre.model

import com.farshad.moviesAppCompose.data.model.network.GenresModel

data class UiGenresModel(
    val genreList: List<GenreWithFavorite>
){
    data class GenreWithFavorite(
        val genre :GenresModel,
        val isSelected: Boolean
    )
}
