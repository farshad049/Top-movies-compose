package com.farshad.topmovies_compose.ui.screnns.filter.model

data class DataForFilterScreen(
    val filteredByGenreList : List<UiFilter> = emptyList(),
    val filteredByImdbList : List<UiFilter> = emptyList()
)

