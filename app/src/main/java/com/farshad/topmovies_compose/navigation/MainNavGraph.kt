package com.farshad.topmovies_compose.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.farshad.topmovies_compose.navigation.NavigationConstants.DASHBOARD_GRAPH
import com.farshad.topmovies_compose.navigation.NavigationConstants.MOVIE_ID
import com.farshad.topmovies_compose.navigation.NavigationConstants.ROOT_GRAPH
import com.farshad.topmovies_compose.ui.screnns.favorite.FavoriteScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.filter.FilterViewModel
import com.farshad.topmovies_compose.ui.screnns.movieDetail.DetailScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.profile.ProfileScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.search.SearchScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.setting.SettingScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.submitMovie.SubmitScreenWithViewModel

@SuppressLint("SuspiciousIndentation")
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

            registerNavGraph(navController= navController)



            composable(route = Screens.Search.route){
                SearchScreenWithViewModel(navController = navController)
            }

            composable(route = Screens.Submit.route){
                SubmitScreenWithViewModel(navController = navController)
            }

            composable(route = Screens.Favorite.route){
                FavoriteScreenWithViewModel(navController = navController)
            }

            composable(route = Screens.Setting.route){
                SettingScreenWithViewModel(navController = navController)
            }

            composable(route = Screens.Profile.route){
                ProfileScreenWithViewModel(navController = navController)
            }




            composable(
                route = Screens.Detail.route,
                arguments = listOf(
                    navArgument(MOVIE_ID){
                        type= NavType.IntType
                        defaultValue = 0
                    }
                ),
                deepLinks = listOf(navDeepLink { uriPattern = "https://moviesapi.ir/api/v1/movies/{$MOVIE_ID}" })
            ){
                val arg= it.arguments?.getInt(MOVIE_ID)
                DetailScreenWithViewModel(navController = navController, arg = arg)
            }







        }
    }





