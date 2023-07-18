package com.farshad.topmovies_compose.ui.screnns.movieListByGenre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.farshad.moviesAppCompose.data.model.domain.DomainMovieModel
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalLazyColumn
import com.farshad.topmovies_compose.ui.screnns.movieListByGenre.model.UiGenresModel


@Composable
fun MovieByGenreScreen(
    modifier: Modifier = Modifier,
    genreList: List<UiGenresModel.GenreWithFavorite>,
    movieList: LazyPagingItems<DomainMovieModel>,
    onGenreClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()

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
                onRowClick = { onMovieClick(it) }
            )
        }
    }
}

@Composable
fun MovieByGenreScreenWithViewModel(
    movieByGenreViewModel: MovieByGenreViewModel = hiltViewModel(),
    safeArg: Int
//    onMovieClick: (Int) -> Unit
) {


    val genreList by movieByGenreViewModel.dataForMovieByGenreScreen.collectAsStateWithLifecycle(
        initialValue = Resource.Loading
    )

    val movieList = movieByGenreViewModel.movieByGenreFlow.collectAsLazyPagingItems()


    when (genreList) {
        is Resource.Success -> {
            MovieByGenreScreen(
                genreList = (genreList as Resource.Success<UiGenresModel>).data.genreList,
                movieList = movieList,
                onGenreClick = { genreId ->
                   // movieByGenreViewModel.updateSelectedGenreId(genreId)
                    movieByGenreViewModel.submitQuery(genreId)
                },
                onMovieClick = {},
            )
        }

        is Resource.Loading -> {
            LoadingAnimation()
        }

        else -> {}
    }

}








