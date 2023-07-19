package com.farshad.topmovies_compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_LIST_GRAPH
import com.farshad.topmovies_compose.ui.screnns.MovieListScreenWithViewModel

fun NavGraphBuilder.movieListNavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
){
    navigation(
        startDestination = Screens.MovieList.route,
        route = MOVIE_LIST_GRAPH
    ){


        composable(route = Screens.MovieList.route){
            MovieListScreenWithViewModel(navController = navController, sharedViewModel = sharedViewModel)
        }

















    }

}