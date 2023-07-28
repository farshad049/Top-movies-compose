package com.farshad.topmovies_compose.ui.screnns.movieListByGenre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.navigation.SharedViewModel
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalLazyColumn
import com.farshad.topmovies_compose.ui.screnns.movieListByGenre.model.UiGenresModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieByGenreScreenWithViewModel(
    navController: NavHostController,
    movieByGenreViewModel: MovieByGenreViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
) {
    val movieByGenreOnClicks= MovieByGenreOnClicks(navController)


    val sharedGenreId= sharedViewModel.genreId
    LaunchedEffect(key1 = sharedGenreId){
        movieByGenreViewModel.submitQuery(sharedGenreId)
    }


    val genreList by movieByGenreViewModel.dataForMovieByGenreScreen.collectAsStateWithLifecycle(
        initialValue = Resource.Loading
    )

    val movieList = movieByGenreViewModel.movieByGenreFlow.collectAsLazyPagingItems()

    val isRefreshing by movieByGenreViewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { movieByGenreViewModel.refresh() })


    when (genreList) {
        is Resource.Success -> {
            MovieByGenreScreen(
                genreList = (genreList as Resource.Success<UiGenresModel>).data.genreList,
                movieList = movieList,
                onGenreClick = { genreId ->
                    movieByGenreViewModel.updateSelectedGenreId(genreId)
                    movieByGenreViewModel.submitQuery(genreId)
                },
                onMovieClick = {movieByGenreOnClicks.onMovieClick(it)},
                refreshState = pullRefreshState,
                isRefreshing = isRefreshing
            )
        }

        is Resource.Loading -> {
            LoadingAnimation()
        }

        else -> {}
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieByGenreScreen(
    modifier: Modifier = Modifier,
    genreList: List<UiGenresModel.GenreWithFavorite>,
    movieList: LazyPagingItems<DomainMovieModel>,
    onGenreClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    refreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
            .background(color = MaterialTheme.colorScheme.background)


    ) {
        Column() {
            GenreFilterLazyRow(
                modifier.padding(horizontal = 8.dp),
                list = genreList,
                onClick = onGenreClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            MovieHorizontalLazyColumn(
                modifier.padding(horizontal = 8.dp),
                movieList = movieList,
                onMovieClick = onMovieClick
            )

        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshing,
            state = refreshState,
        )
    }
}











