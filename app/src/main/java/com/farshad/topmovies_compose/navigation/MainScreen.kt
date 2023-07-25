package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.farshad.topmovies_compose.navigation.NavigationConstants.SEARCH_SCREEN
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navHostController: NavHostController) {


    var showBottomAndTopBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    showBottomAndTopBar =
        when (navBackStackEntry?.destination?.route) {
            SEARCH_SCREEN -> false // on this screen bottom bar should be hidden
            else -> true // in all other cases show bottom bar
        }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                scope = scope,
                drawerState= drawerState,
                navHostController = navHostController
            )

        },
        content = {
            Scaffold(
                topBar = {
                    if (showBottomAndTopBar) {
                        MyTopBar(
                            onTopBarClick = { navHostController.navigate(Screens.Search.route) },
                            onDrawerClick = {
                                scope.launch {
                                    drawerState.open()
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
    )




}













