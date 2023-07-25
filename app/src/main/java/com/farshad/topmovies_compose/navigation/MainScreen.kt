package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.farshad.topmovies_compose.navigation.NavigationConstants.SEARCH_SCREEN
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navHostController: NavHostController) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var showBottomAndTopBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    showBottomAndTopBar =
        when (navBackStackEntry?.destination?.route) {
            SEARCH_SCREEN -> false // on this screen bottom bar should be hidden
            else -> true // in all other cases show bottom bar
        }


    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer()
        },
        topBar = {
            if (showBottomAndTopBar) {
                MyTopBar(
                    onTopBarClick = { navHostController.navigate(Screens.Search.route) },
                    onDrawerClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomAndTopBar) {
                BottomBar(navHostController = navHostController)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            SetupNavGraph(navController = navHostController)
        }

    }

}













