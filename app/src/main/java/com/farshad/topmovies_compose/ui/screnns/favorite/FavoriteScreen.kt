package com.farshad.topmovies_compose.ui.screnns.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.topmovies_compose.navigation.Screens
import com.farshad.topmovies_compose.ui.screnns.common.ImageWithGradient

@Composable
fun FavoriteScreenWithViewModel(
    navController: NavHostController,
    favoriteScreenViewModel: FavoriteScreenViewModel = hiltViewModel()
) {
    favoriteScreenViewModel.getFavoriteMovieList()

    val movieList by favoriteScreenViewModel.listOfFavoriteMovie.collectAsState()

    when {
        movieList.isNotEmpty() -> {
            FavoriteScreen(
                movieList = movieList,
                onClick = { navController.navigate(Screens.Detail.passMovieID(it)) }
            )
        }

        movieList.isEmpty() -> {
            NoFavorite()
        }
    }


}


@Composable
fun FavoriteScreen(
    movieList: List<FavoriteMovieEntity>,
    onClick: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val listState = rememberLazyGridState()

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                columns = GridCells.Adaptive(130.dp),
                state = listState
            ) {
                itemsIndexed(
                    items = movieList, key = { index, item -> item.hashCode() }
                ) { index, item ->
                    MovieThumbnailGridItem(
                        movie = item,
                        onClick = onClick
                    )
                }
            }

        }
    }


}


@Composable
fun MovieThumbnailGridItem(
    modifier: Modifier = Modifier,
    movie: FavoriteMovieEntity,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .width(130.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageWithGradient(
            modifier = modifier,
            width = 130.dp,
            height = 180.dp,
            movieId = movie.id,
            imageUrl = movie.poster,
            onClick = { onClick(it) }
        )

        Text(
            modifier = Modifier.width(130.dp),
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        )
    }
}


@Composable
fun NoFavorite() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.you_have_no_favorite_movie),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}













