package com.farshad.topmovies_compose.ui.screnns.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.navigation.SharedViewModel
import com.farshad.topmovies_compose.ui.screnns.common.HeaderWithViewAll
import com.farshad.topmovies_compose.ui.screnns.common.ImageThumbnailRow
import com.farshad.topmovies_compose.ui.screnns.common.LoadingWithTryAgain
import com.farshad.topmovies_compose.ui.screnns.common.SuggestionChipLazyRow
import com.farshad.topmovies_compose.ui.screnns.dashboard.model.DashboardUiModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleGenreList
import com.farshad.topmovies_compose.util.sampleMovieList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreenWithViewModel(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
) {
    val dashboardOnClicks= DashboardOnClicks(navController)

    val data by dashboardViewModel.combinedData.collectAsStateWithLifecycle(initialValue = Resource.Loading)
    val isRefreshing by dashboardViewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { dashboardViewModel.refresh() })

    when (data) {
        is Resource.Success -> {
            DashboardScreen(
                movieAndGenre = (data as Resource.Success<DashboardUiModel>).data,
                onImageClick = {dashboardOnClicks.onMovieClick(it)},
                onGenreClick = {genreId->
                    sharedViewModel.updateSelectedGenreId(genreId)
                    dashboardOnClicks.onGenreClick()
                },
                onViewAllGenreClick = {dashboardOnClicks.onGenreClick()},
                onViewAllMovieClick = {dashboardOnClicks.onViewAllMovieClick()},
                refreshState = pullRefreshState,
                isRefreshing = isRefreshing
            )
        }

        is Resource.Loading -> {
            LoadingWithTryAgain(
                onTryAgainClick = {
                    dashboardViewModel.refresh()
                }
            )
        }
        else -> {}
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    movieAndGenre: DashboardUiModel,
    onImageClick: (Int) -> Unit,
    onGenreClick: (Int) -> Unit,
    onViewAllGenreClick: () -> Unit,
    onViewAllMovieClick : ()-> Unit,
    refreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
            .background(color = MaterialTheme.colorScheme.background)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(4.dp))

            DashboardImageThumbnailRow(
                modifier = Modifier.height(310.dp),
                movies = movieAndGenre.randomMovies,
                onClick = onImageClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            HeaderWithViewAll(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                title = stringResource(id = R.string.categories),
                onViewAllClick = onViewAllGenreClick
            )

            SuggestionChipLazyRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                list = movieAndGenre.genre,
                onClick = { onGenreClick(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HeaderWithViewAll(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                title = stringResource(id = R.string.top_rated_movies),
                onViewAllClick = onViewAllMovieClick
            )

            Spacer(modifier = Modifier.height(4.dp))

            ImageThumbnailRow(
                movies = movieAndGenre.movie,
                onClick = onImageClick
            )

            Spacer(modifier = Modifier.height(24.dp))


        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshing,
            state = refreshState,
        )

    }
}




@OptIn(ExperimentalMaterialApi::class)
@DarkAndLightPreview
@Composable
private fun Preview(
) {
    AppTheme() {
        DashboardScreen(
            movieAndGenre = DashboardUiModel(
                movie = sampleMovieList,
                genre = sampleGenreList,
                randomMovies = sampleMovieList
            ),
            onImageClick = {},
            onGenreClick = {},
            onViewAllGenreClick = {},
            onViewAllMovieClick = {},
            refreshState = rememberPullRefreshState(refreshing = false, onRefresh = { /*TODO*/ }),
            isRefreshing = false
        )
    }
}