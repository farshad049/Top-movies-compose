package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.dataStore.DataStoreViewModel
import com.farshad.topmovies_compose.ui.screnns.userInfo.UserInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerItem(
    val icon: ImageVector,
    val title: String,
    val route: String
)

@Composable
fun Drawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
    userInfoViewModel: UserInfoViewModel= hiltViewModel()
) {
    val isLoggedIn by dataStoreViewModel.isLoggedIn.collectAsState(initial = false)

    val useName by userInfoViewModel.userInfoFlow.collectAsState()

    val items = listOf(
        DrawerItem(
            icon = Icons.Rounded.Favorite,
            title = stringResource(id = R.string.favorite_movies),
            route = Screens.Favorite.route
        ),
        DrawerItem(
            icon = Icons.Default.Settings,
            title = stringResource(id = R.string.setting),
            route = Screens.Favorite.route
        ),
        DrawerItem(
            icon = Icons.Default.Person,
            title = stringResource(id = R.string.profile),
            route = Screens.Favorite.route
        )

    )

    var selectedItem by remember { mutableStateOf(items[0]) }

    ModalDrawerSheet {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.TopCenter)) {

                Spacer(Modifier.height(12.dp))

                DrawerLottieHeader(
                    lottieCompositionSpec = LottieCompositionSpec.RawRes(R.raw.camera)
                )

                if (isLoggedIn){
                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        icon = {},
                        label = { Text(text = "Welcome ${useName?.name}") },
                        selected = false,
                        onClick = {},
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    Spacer(Modifier.height(24.dp))
                }

                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = item == selectedItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem = item
                            navHostController.navigate(route = item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }

            if (isLoggedIn){
                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Logout, contentDescription = null) },
                    label = { Text(text = stringResource(id = R.string.log_out)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        dataStoreViewModel.deleteToken()
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .align(Alignment.BottomCenter)
                )
            }

            Spacer(Modifier.height(12.dp))
        }


    }


}


@Composable
fun DrawerLottieHeader(
    lottieCompositionSpec: LottieCompositionSpec,
) {
    val composition by rememberLottieComposition(spec = lottieCompositionSpec)
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .clickable { isPlaying = true },
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(230.dp),
            composition = composition,
            progress = { progress }
        )


    }
}




