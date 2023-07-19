package com.farshad.topmovies_compose.ui.screnns.movieDetail

import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.navigation.Screens


class DetailScreenOnClicks(private val navController: NavHostController) {

    fun onSimilarMovieClick(movieId:Int){
        navController.navigate(Screens.Detail.passMovieID(movieId))
    }


}