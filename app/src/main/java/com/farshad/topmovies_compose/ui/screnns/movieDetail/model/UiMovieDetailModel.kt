package com.farshad.topmovies_compose.ui.screnns.movieDetail.model

import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel

data class UiMovieDetailModel (
    val movie : DomainMovieModel,
    val isFavorite : Boolean,
    val similarMovies : List<DomainMovieModel>
        )