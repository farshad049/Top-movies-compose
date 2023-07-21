package com.farshad.topmovies_compose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.farshad.topmovies_compose.navigation.NavigationConstants.DASHBOARD_GRAPH
import com.farshad.topmovies_compose.navigation.NavigationConstants.ROOT_GRAPH
import com.farshad.topmovies_compose.ui.screnns.filter.FilterViewModel
import com.farshad.topmovies_compose.ui.screnns.movieDetail.DetailScreenWithViewModel

@Composable
fun SetupNavGraph(navController: NavHostController){

    val sharedViewModel: SharedViewModel= hiltViewModel()

    val filterViewModel: FilterViewModel= hiltViewModel()


        NavHost(
            navController = navController,
            startDestination = DASHBOARD_GRAPH,
            route = ROOT_GRAPH
        ){

            dashboardNavGraph(navController = navController,sharedViewModel)

            movieListNavGraph(navController= navController, filterViewModel)


            composable(
                route = Screens.Detail.route,
                arguments = listOf(
                    navArgument(NavigationConstants.MOVIE_ID){
                        type= NavType.IntType
                        defaultValue = 0
                    }
                )
            ){
                val arg= it.arguments?.getInt(NavigationConstants.MOVIE_ID)
                DetailScreenWithViewModel(navController = navController, arg = arg)
            }







        }
    }


