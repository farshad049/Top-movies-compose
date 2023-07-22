package com.farshad.topmovies_compose.ui.screnns.movieList

import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.navigation.Screens
import com.farshad.topmovies_compose.ui.screnns.filter.FilterViewModel

class MovieListOnClicks(
    private val navHostController: NavHostController,
    private val filterViewModel: FilterViewModel
    ) {

    fun onMovieClick(movieId: Int){
        navHostController.navigate(Screens.Detail.passMovieID(movieId))
    }

    fun onFilterIconClick(){
        navHostController.navigate(Screens.Filter.route)
    }

    fun onFilterRowItemClick(filterToRemove: String){
        filterViewModel.onMovieListFilterRowItemClick(filterToRemove = filterToRemove)
    }
}