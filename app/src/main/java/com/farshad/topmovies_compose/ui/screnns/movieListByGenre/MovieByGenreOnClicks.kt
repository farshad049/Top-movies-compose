package com.farshad.topmovies_compose.ui.screnns.movieListByGenre

import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.navigation.Screens

class MovieByGenreOnClicks(private val navHostController: NavHostController) {

    fun onMovieClick(movieId: Int){
        navHostController.navigate(Screens.Detail.passMovieID(movieId))
    }
}