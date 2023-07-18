package com.farshad.topmovies_compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.farshad.topmovies_compose.navigation.NavigationConstants.DASHBOARD_GRAPH
import com.farshad.topmovies_compose.ui.screnns.dashboard.DashboardScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.movieListByGenre.MovieByGenreScreenWithViewModel

fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
){
    navigation(
        startDestination = Screens.Dashboard.route,
        route = DASHBOARD_GRAPH
    ){


        composable(route = Screens.Dashboard.route){
            DashboardScreenWithViewModel(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = Screens.MovieByGenre.route){
            MovieByGenreScreenWithViewModel(navController = navController, sharedViewModel = sharedViewModel)
        }















    }

}