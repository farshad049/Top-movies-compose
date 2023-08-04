package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.farshad.topmovies_compose.data.dataStore.DataStoreViewModel

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
) {

    val isLoggedIn by dataStoreViewModel.isLoggedIn.collectAsState(initial = false)

    val pages: Set<Screens> =
        if (isLoggedIn) {
            setOf(
                Screens.Dashboard,
                Screens.MovieList,
                Screens.Submit,
                Screens.Favorite
            )
        } else {
            setOf(
                Screens.Dashboard,
                Screens.MovieList,
                Screens.Register
            )
        }


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
        selectedContentColor = MaterialTheme.colorScheme.onBackground,
        selected = currentDestination?.hierarchy?.any() {
            it.route == screen.route
        } == true,
        unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), // toma make the unSelected Item even less visible
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
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val backGroundColor = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Gray.copy(alpha = 0.5f),

            )
    )

    val borderColor = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
        )
    )

    val shape = RoundedCornerShape(
        topStart = 25.dp,
        topEnd = 25.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                brush = borderColor,
                shape = shape
            )
            .background(
                brush = backGroundColor,
                shape = shape
            )
            .clip(
                shape = shape
            )

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}