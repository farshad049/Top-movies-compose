package com.farshad.topmovies_compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalMovies
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.farshad.topmovies_compose.navigation.NavigationConstants.DASHBOARD_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.DETAIL_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.Filter_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_BY_GENRE_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_ID
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_LIST_SCREEN

sealed class Screens(
    val icon: ImageVector?,
    val route: String
    ){

    object Dashboard: Screens(
        icon = Icons.Rounded.Home,
        route = DASHBOARD_SCREEN
    )


    object MovieByGenre: Screens(
        icon = null,
        route = MOVIE_BY_GENRE_SCREEN
    )

    object MovieList: Screens(
        icon = Icons.Rounded.LocalMovies,
        route = MOVIE_LIST_SCREEN
    )
    
    object Filter: Screens(
        icon = null,
        route = Filter_SCREEN
    )


    object Detail: Screens(
        icon = null,
        route = "$DETAIL_SCREEN?movieId={$MOVIE_ID}"
    ){
        fun passMovieID(movieId: Int= 0): String{
            return "$DETAIL_SCREEN?movieId=$movieId"
        }
    }













}
