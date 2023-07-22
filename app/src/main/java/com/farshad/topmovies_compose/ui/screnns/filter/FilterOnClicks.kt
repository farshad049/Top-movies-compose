package com.farshad.topmovies_compose.ui.screnns.filter

class FilterOnClicks(private val filterViewModel: FilterViewModel) {


    fun onGenreFilterClick(selectedFilter : String){
        filterViewModel.onGenreFilterClick(selectedFilter)
    }

    fun onImdbFilterClick(selectedFilter : String){
        filterViewModel.onImdbFilterClick(selectedFilter)
    }





}