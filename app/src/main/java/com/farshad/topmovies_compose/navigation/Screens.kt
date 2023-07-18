package com.farshad.topmovies_compose.navigation

import com.farshad.topmovies_compose.navigation.NavigationConstants.DASHBOARD_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.DETAIL_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_BY_GENRE_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_ID

sealed class Screens(val route: String){

    object Dashboard: Screens(route = DASHBOARD_SCREEN)


    object MovieByGenre: Screens(route = MOVIE_BY_GENRE_SCREEN)


    object Detail: Screens(route = "$DETAIL_SCREEN?movieId={$MOVIE_ID}"){
        fun passMovieID(movieId: Int= 0): String{
            return "$DETAIL_SCREEN?movieId=$movieId"
        }
    }













}
