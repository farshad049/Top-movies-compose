package com.farshad.topmovies_compose.ui.screnns.dashboard.model

import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.moviesAppCompose.data.model.network.GenresModel

data class DashboardUiModel(
    val movie: List<DomainMovieModel>,
    val genre: List<GenresModel>,
    val randomMovies: List<DomainMovieModel>
)
