package com.farshad.topmovies_compose.ui.screnns.movieList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalLazyColumn
import com.farshad.topmovies_compose.ui.screnns.filter.FilterViewModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview


@Composable
fun MovieListScreenWithViewModel(
    navController: NavHostController,
    filterViewModel: FilterViewModel,
    movieListViewModel: MovieListViewModel= hiltViewModel()
){
    val movieListOnClicks= MovieListOnClicks(navHostController = navController, filterViewModel = filterViewModel)

    val movieList= movieListViewModel.movieListFlow.collectAsLazyPagingItems()

    val filterForFilterRow by filterViewModel.combinedDataForFilterRowMovieList.collectAsStateWithLifecycle()

    val filterForMovieList by filterViewModel.combinedFilterDataForMovieList.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = filterForMovieList ){
        movieListViewModel.submitQuery(filterForMovieList)
    }

    MovieListScreen(
        movieList = movieList,
        onMovieClick = {movieListOnClicks.onMovieClick(it)},
        filtersForLazyRow = filterForFilterRow,
        onFilterRowItemClick = {movieListOnClicks.onFilterRowItemClick(it)},
        onFilterIconClick = {movieListOnClicks.onFilterIconClick()}
    )

}


@Composable
fun MovieListScreen(
    movieList: LazyPagingItems<DomainMovieModel>,
    onMovieClick: (Int)-> Unit,
    filtersForLazyRow: Set<String>,
    onFilterRowItemClick: (String) -> Unit,
    onFilterIconClick: () -> Unit
){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {

            FilterRow(
                filters = filtersForLazyRow,
                onChipClick = onFilterRowItemClick,
                onFilterIconClick = onFilterIconClick
            )

            MovieHorizontalLazyColumn(
                movieList = movieList,
                onMovieClick = onMovieClick,
            )
        }

    }

}

@Composable
fun FilterRow(
    filters: Set<String>,
    onChipClick: (String) -> Unit,
    onFilterIconClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        InputChipLazyRow(
            modifier = Modifier.padding(horizontal = 4.dp),
            list = filters,
            onClick = onChipClick
        )

        IconButton(
            onClick = { onFilterIconClick() }
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Rounded.FilterAlt,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
        }
    }
}


@Composable
fun InputChipLazyRow(
    modifier: Modifier= Modifier,
    list: Set<String>,
    onClick: (String)-> Unit
){
    val listForRow = list.toList()
    val listState= rememberLazyListState()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ){
        items(
            items = listForRow, key = {it.hashCode()}
        ){item->
            InputChipItem(
                filterName = item,
                onChipClick = {onClick(it)}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputChipItem(
    filterName: String,
    onChipClick: (String)-> Unit
){
    InputChip(
        selected = true,
        onClick = { onChipClick(filterName)},
        label = { Text(text = filterName)},
        elevation = InputChipDefaults.inputChipElevation(
            elevation = 3.dp
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Close,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
        }
    )

}






@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        FilterRow(
            filters = setOf("asas","asass","asasas"),
            onChipClick = {},
            onFilterIconClick = {}
        )

    }
}

