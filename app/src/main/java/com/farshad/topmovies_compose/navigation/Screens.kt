package com.farshad.topmovies_compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Filter
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryAdd
import androidx.compose.material.icons.rounded.LocalMovies
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.farshad.topmovies_compose.navigation.NavigationConstants.DASHBOARD_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.DETAIL_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.FAVORITE_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.Filter_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.LOGIN_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_BY_GENRE_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_ID
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_LIST_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.PROFILE_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.REGISTER_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.SEARCH_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.SETTING_SCREEN
import com.farshad.topmovies_compose.navigation.NavigationConstants.SUBMIT_SCREEN

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
        icon = Icons.Rounded.Filter,
        route = Filter_SCREEN
    )

    object Search: Screens(
        icon = null,
        route = SEARCH_SCREEN
        )


    object Register: Screens(
        icon= Icons.Rounded.Login,
        route = REGISTER_SCREEN
    )

    object Login: Screens(
        icon = Icons.Rounded.Login,
        route = LOGIN_SCREEN
    )

    object Submit: Screens(
        icon = Icons.Rounded.LibraryAdd,
        route = SUBMIT_SCREEN
    )

    object Favorite: Screens(
        icon = Icons.Rounded.Favorite,
        route = FAVORITE_SCREEN
    )

    object Profile: Screens(
        icon = Icons.Rounded.Person,
        route = PROFILE_SCREEN
    )

    object Setting: Screens(
        icon = Icons.Rounded.Settings,
        route = SETTING_SCREEN
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
