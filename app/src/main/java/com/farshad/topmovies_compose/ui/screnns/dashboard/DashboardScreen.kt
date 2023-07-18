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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.screnns.common.HeaderWithViewAll

import com.farshad.topmovies_compose.ui.screnns.dashboard.model.DashboardUiModel

import com.farshad.topmovies_compose.ui.screnns.common.ImageThumbnailRow
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.common.SuggestionChipLazyRow
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleGenreList
import com.farshad.topmovies_compose.util.sampleMovieList


@Composable
fun DashboardScreen(
    movieAndGenre: DashboardUiModel,
    onImageClick: (Int) -> Unit,
    onGenreClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            DashboardImageThumbnailRow(
                modifier = Modifier.height(310.dp),
                movies = movieAndGenre.randomMovies,
                onClick = onImageClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            HeaderWithViewAll(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                title = stringResource(id = R.string.categories),
                onViewAllClick = {}
            )

            SuggestionChipLazyRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                list = movieAndGenre.genre,
                onClick = { onGenreClick(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            HeaderWithViewAll(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                title = stringResource(id = R.string.top_rated_movies),
                onViewAllClick = {}
            )

            Spacer(modifier = Modifier.height(4.dp))

            ImageThumbnailRow(
                movies = movieAndGenre.movie,
                onClick = {}
            )

        }

    }
}

@Composable
fun DashboardScreenWithViewModel(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    dashboardOnClicks: DashboardOnClicks
) {
    val data by dashboardViewModel.combinedData.collectAsStateWithLifecycle(initialValue = Resource.Loading)

    when (data) {
        is Resource.Success -> {
            DashboardScreen(
                movieAndGenre = (data as Resource.Success<DashboardUiModel>).data,
                onImageClick = {},
                onGenreClick = {}
            )
        }

        is Resource.Loading -> {
            LoadingAnimation()
        }
        else -> {}
    }

}


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
            onGenreClick = {}
        )
    }
}