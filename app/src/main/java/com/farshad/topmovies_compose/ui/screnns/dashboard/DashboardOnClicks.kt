package com.farshad.topmovies_compose.ui.screnns.dashboard


import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.navigation.Screens

class DashboardOnClicks(private val navController: NavHostController) {
    fun onGenreClick(){
        navController.navigate(Screens.MovieByGenre.route)
    }

    fun onMovieClick(movieId:Int){
        navController.navigate(Screens.Detail.passMovieID(movieId))
    }

    fun onViewAllMovieClick(){
        navController.navigate(Screens.MovieList.route)
    }
}