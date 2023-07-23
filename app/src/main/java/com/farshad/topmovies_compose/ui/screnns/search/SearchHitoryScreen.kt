package com.farshad.topmovies_compose.ui.screnns.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.db.Entity.SearchHistoryEntityWithoutId
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun SearchHistoryScreenWithViewModel(
    searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()
) {
    val searchHistoryOnClicks =
        SearchHistoryOnClicks(searchHistoryViewModel = searchHistoryViewModel)
    val movieList by searchHistoryViewModel.searchHistoryListFlow.collectAsStateWithLifecycle()

    SearchHistoryScreen(
        movieHistoryList = movieList,
        onDeleteAllClick = { searchHistoryOnClicks.onDeleteAllMovieClick() },
        onDeleteMovieClick = { searchHistoryOnClicks.onDeleteMovieItemClick(it) }
    )
}


@Composable
fun SearchHistoryScreen(
    movieHistoryList: List<SearchHistoryEntityWithoutId>,
    onDeleteAllClick: () -> Unit,
    onDeleteMovieClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .shadow(
                        elevation = 3.dp,
                        spotColor = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                    .padding(2.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onDeleteAllClick() }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector =Icons.Rounded.DeleteSweep ,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )

                }
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = R.string.delete_all),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            val listState = rememberLazyListState()


            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = listState
            ) {
                items(
                    items = movieHistoryList, key = { it.movieId }
                ) { movie ->
                    SearchHistoryItem(
                        movie = movie,
                        onDeleteMovieClick = { onDeleteMovieClick(it) }
                    )

                }
            }


        }
    }
}

@Composable
fun SearchHistoryItem(
    movie: SearchHistoryEntityWithoutId,
    onDeleteMovieClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                spotColor = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.small
            )
            .background(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
            .clip(shape = MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.movieTitle,
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { onDeleteMovieClick(movie.movieId) },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    }

}


@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        SearchHistoryScreen(
            movieHistoryList = listOf(
                SearchHistoryEntityWithoutId(movieId = 1, movieTitle ="barman"),
                SearchHistoryEntityWithoutId(movieId = 2, movieTitle ="batman1"),
                SearchHistoryEntityWithoutId(movieId = 3, movieTitle ="barman6")
            ),
            onDeleteMovieClick = {},
            onDeleteAllClick = {}
        )
    }

}