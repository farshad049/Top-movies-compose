package com.farshad.topmovies_compose.ui.screnns.movieList

import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.navigation.Screens

class MovieListOnClicks(private val navHostController: NavHostController) {

    fun onMovieClick(movieId: Int){
        navHostController.navigate(Screens.Detail.passMovieID(movieId))
    }

    fun onFilterIconClick(){
        navHostController.navigate(Screens.Filter.route)
    }
}