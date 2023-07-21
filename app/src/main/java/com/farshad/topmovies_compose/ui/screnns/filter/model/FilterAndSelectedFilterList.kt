package com.farshad.topmovies_compose.ui.screnns.filter.model

data class FilterAndSelectedFilterList (
    val filters: Set<String> = setOf(),
    var selectedFilters: Set<String> = emptySet()
        )