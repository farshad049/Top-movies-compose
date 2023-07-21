package com.farshad.topmovies_compose.ui.screnns.filter.model

data class ModelDataForMovieList (
    val genreSetOfSelectedFilters : Set<String> = emptySet(),
    val imdbSetOfSelectedFilters : Set<String> = emptySet()
        )