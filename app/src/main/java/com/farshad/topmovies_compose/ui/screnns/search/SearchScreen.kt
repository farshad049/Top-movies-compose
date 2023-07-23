package com.farshad.topmovies_compose.ui.screnns.search

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalLazyColumn

@Composable
fun SearchScreenWithViewModel(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    searchHistoryViewModel: SearchHistoryViewModel= hiltViewModel()
) {
    val searchOnClicks= SearchOnClicks(
        navHostController = navController,
        searchViewModel = searchViewModel,
        searchHistoryViewModel= searchHistoryViewModel
    )

    val movieList= searchViewModel.searchFlow.collectAsLazyPagingItems()
    val movieHistoryItemCount by searchHistoryViewModel.searchHistoryListFlow.collectAsState()

    SearchScreen(
        movieList = movieList,
        onMovieClick = {id, title ->searchOnClicks.onMovieClick(id, title)},
        onSearchClick = {searchOnClicks.onSearchClick(it)},
        searchViewModel = searchViewModel,
        movieHistoryItemCount= movieHistoryItemCount.size
    )
}


@Composable
fun SearchScreen(
    movieList: LazyPagingItems<DomainMovieModel>,
    onMovieClick: (Int, String)-> Unit,
    onSearchClick: (String) -> Unit,
    searchViewModel: SearchViewModel,
    movieHistoryItemCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchWidget(
                onSearchClicked = {onSearchClick(it)},
                viewModel = searchViewModel
            )

            Spacer(modifier = Modifier.height(12.dp))

            MovieHorizontalLazyColumnSearchScreen(
                movieList = movieList,
                onMovieClick = onMovieClick,
                userSearch = searchViewModel.userSearch,
                movieHistoryItemCount= movieHistoryItemCount
            )

        }
    }
}

@Composable
fun SearchWidget(
    onSearchClicked: (String) -> Unit,
    viewModel: SearchViewModel
){
    var text by rememberSaveable { mutableStateOf("") }
    val shape = RoundedCornerShape(35.dp)
    val backgroundColor= Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
            Color.Transparent,
        )
    )

     val handler = Handler(Looper.getMainLooper())
     val searchRunnable = Runnable { viewModel.submitQuery(text) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                shape = shape
            )
            .shadow(
                elevation = 8.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(35.dp)
            )
            .background(
                brush = backgroundColor,
                shape = shape
            )
            .clip(shape = shape)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "TextField"
                },
            value = text,
            onValueChange = {
                text = it
                handler.removeCallbacks(searchRunnable)
                handler.postDelayed(searchRunnable,500L)
                            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    text = stringResource(id = R.string.type_to_search),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "CloseButton"
                        },
                    onClick = { text = "" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor =MaterialTheme.colorScheme.primary
            )
        )
    }
}