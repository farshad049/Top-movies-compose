package com.farshad.topmovies_compose.util

import com.farshad.moviesAppCompose.data.model.domain.DomainMovieModel

object MovieCache {
    val movieMap= mutableMapOf<Int, DomainMovieModel>()

    val similarMovieMap= mutableMapOf<Int,List<DomainMovieModel>>()
}