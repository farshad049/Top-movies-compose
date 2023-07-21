package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalItemShimmer
import com.farshad.topmovies_compose.util.DarkAndLightPreview


@Composable
fun MainScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = { MyTopBar(onTopBarClick = {})},
        bottomBar = { BottomBar(navHostController = navHostController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
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
    val backGroundColor=Brush.verticalGradient(
        colors = listOf(
            Color.Transparent ,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
        )
    )

    val borderColor=Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f) ,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
        )
    )

    val shape= RoundedCornerShape(
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
                shape= shape
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



@Composable
fun MyTopBar(
    modifier: Modifier= Modifier,
    onTopBarClick: () -> Unit
){
    val shape = RoundedCornerShape(35.dp)

    val backgroundColor= Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
            Color.Transparent,
        )
    )

    Box(
        modifier = modifier
            .padding(top = 12.dp)
            .padding(horizontal = 8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                shape = shape
            )
            .fillMaxWidth()
            .height(50.dp)
            .shadow(
                elevation = 8.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(35.dp)
            )
            .background(
                brush = backgroundColor,
                shape= shape
            )
            .clip(shape =shape )
            .clickable { onTopBarClick }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxSize()
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription ="",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.type_to_search),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@DarkAndLightPreview
@Composable
private fun Preview(){
    MyTopBar(
        onTopBarClick = {}
    )
}





