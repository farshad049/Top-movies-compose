package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.farshad.topmovies_compose.navigation.NavigationConstants.SEARCH_SCREEN
import com.farshad.topmovies_compose.util.CheckInternetConnection
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navHostController: NavHostController) {

    val context= LocalContext.current
    var connectionLiveData= CheckInternetConnection(context)

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

            val isInternetConnected= connectionLiveData.observeAsState().value
         //   if (isInternetConnected == true){
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
//            }else{
//                NoInternetScreen()
//            }

        }
    )




}













