package com.farshad.topmovies_compose.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_LIST_GRAPH
import com.farshad.topmovies_compose.ui.screnns.filter.FilterScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.filter.FilterViewModel
import com.farshad.topmovies_compose.ui.screnns.movieList.MovieListScreenWithViewModel

fun NavGraphBuilder.movieListNavGraph(
    navController: NavHostController,
    filterViewModel: FilterViewModel
){

    navigation(
        startDestination = Screens.MovieList.route,
        route = MOVIE_LIST_GRAPH
    ){


        composable(route = Screens.MovieList.route){
            MovieListScreenWithViewModel(navController = navController, filterViewModel= filterViewModel)
        }

        composable(route = Screens.Filter.route){
            FilterScreenWithViewModel(navController = navController, filterViewModel= filterViewModel)
        }



















    }

}