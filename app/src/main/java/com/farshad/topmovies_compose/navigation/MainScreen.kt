package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainScreen(navHostController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navHostController = navHostController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())//set bottom padding for main activity in order so content could be shown without the bottom of them become clipped
        ) {
            SetupNavGraph(navController = navHostController)
        }

    }
}




@Composable
fun BottomBar(
    modifier: Modifier= Modifier,
    navHostController: NavHostController
) {

    val pages = setOf(
        Screens.Dashboard,
        Screens.MovieList
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MyBottomNavigation(modifier = modifier) {
        pages.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navHostController = navHostController
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: Screens,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = screen.icon!!,
                contentDescription = "navigation icon"
            )
        },
        selected = currentDestination?.hierarchy?.any() {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled), // toma make the unSelected Item even less visible
        onClick = {
            navHostController.navigate(screen.route) {
                popUpTo(navHostController.graph.findStartDestination().id) // get back to home screen when press back bottom
                launchSingleTop =
                    true // the second touch on back bottom will exit the application since other copies of the start destination Id have been removed from backStack throw this piece of ode
            }
        }
    )
}


@Composable
fun MyBottomNavigation(
    modifier: Modifier= Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 6.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 5.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 5.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 5.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = size.height / 2,
                    endY = size.height
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            }
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}






