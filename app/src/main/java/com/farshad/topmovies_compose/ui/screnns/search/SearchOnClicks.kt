package com.farshad.topmovies_compose.ui.screnns.search

import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.navigation.Screens

class SearchOnClicks(
    private val navHostController: NavHostController,
    private val searchViewModel: SearchViewModel
    ) {

    fun onMovieClick(movieId: Int){
        navHostController.navigate(Screens.Detail.passMovieID(movieId))
    }

    fun onSearchClick(query: String){
        searchViewModel.submitQuery(query)
    }
}