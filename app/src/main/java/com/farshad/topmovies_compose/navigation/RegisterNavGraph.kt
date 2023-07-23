package com.farshad.topmovies_compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.farshad.topmovies_compose.ui.screnns.login.LoginScreenWithViewModel
import com.farshad.topmovies_compose.ui.screnns.register.RegisterScreenWithViewModel

fun NavGraphBuilder.registerNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screens.Register.route,
        route = NavigationConstants.REGISTER_GRAPH
    ){


        composable(route = Screens.Register.route){
            RegisterScreenWithViewModel(navController = navController)
        }

        composable(route = Screens.Login.route){
            LoginScreenWithViewModel(navController = navController)
        }















    }

}