package com.farshad.topmovies_compose.ui.screnns.movieDetail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalItemShimmerList
import com.farshad.topmovies_compose.ui.screnns.movieDetail.model.UiMovieDetailModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.ui.theme.myRed
import com.farshad.topmovies_compose.ui.theme.myYellow
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleMovie1
import com.farshad.topmovies_compose.util.sampleMovieList

@Composable
fun DetailScreenWithViewModel(
    navController: NavHostController,
    detailViewModel: MovieDetailViewModel = hiltViewModel(),
    arg: Int?
) {
    arg?.let { detailViewModel.getMovieById(arg) }

    val data by detailViewModel.combinedData.collectAsStateWithLifecycle(initialValue = Resource.Loading)

    when (data) {
        is Resource.Success -> {

        }

        is Resource.Loading -> {
            LoadingAnimation()
        }

        else -> {}
    }

}

@Composable
fun DetailScreen(
    movieItem: UiMovieDetailModel,
    onFavoriteClick: (Int) -> Unit,
    onShareClick: (Int) -> Unit
) {

    val lazyListState= rememberLazyListState()
    val height= 260.dp
    val animationDuration= 500

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        DetailScreeUp(
            lazyListState = lazyListState,
            animationDuration = animationDuration,
            height = height,
            movie = movieItem
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailScreenDown(
            lazyListState = lazyListState,
            animationDuration = animationDuration,
            height = height,
            movie = movieItem,
            onFavoriteClick = {onFavoriteClick(it)},
            onShareClick = {onShareClick(it)}
        )
    }




}

@Composable
fun DetailScreeUp(
    lazyListState: LazyListState,
    animationDuration: Int,
    height: Dp,
    movie: UiMovieDetailModel
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = animationDuration))
            .height(height = if (lazyListState.isScrolled) 0.dp else height)
            .shadow(
                elevation = 6.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )

    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data("painterResource(id = R.drawable.image)")
                .crossfade(500)
                .error(R.drawable.error)
                .build(),
            placeholder = painterResource(id = R.drawable.place_holder) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier
                .padding(start = 22.dp, bottom = 15.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                modifier = Modifier
                    .background(
                        color = myYellow.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(4.dp)
                    ),
                text = " IMDB ${movie.movie.imdb_rating}/10 "
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 2.dp),
                text = movie.movie.runTime,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
fun DetailScreenDown(
    lazyListState: LazyListState,
    animationDuration: Int,
    height: Dp,
    movie: UiMovieDetailModel,
    onFavoriteClick: (Int) -> Unit,
    onShareClick: (Int) -> Unit
){
    val padding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else height,
        animationSpec = tween(durationMillis = animationDuration)
    )

    LazyColumn(
        modifier = Modifier.padding(top = padding),
        state = lazyListState
    ) {
        item {
            DetailScreenContent(
                movie = movie,
                onFavoriteClick = {onFavoriteClick(it)},
                onShareClick = {onShareClick(it)}
            )
        }

    }
}

@Composable
fun DetailScreenContent(
    movie: UiMovieDetailModel,
    onFavoriteClick: (Int)-> Unit,
    onShareClick: (Int)-> Unit
){
    Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(all = 4.dp),
                        text = stringResource(id = R.string.rated),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.9f)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.movie.rated,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onFavoriteClick(movie.movie.id)}) {
                        Icon(
                            modifier = Modifier.size(45.dp),
                            imageVector = if (movie.isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = "",
                            tint = myRed
                        )
                    }
                    IconButton(onClick = { onShareClick(movie.movie.id) }) {
                        Icon(
                            modifier = Modifier.size(45.dp),
                            imageVector = Icons.Rounded.Share,
                            contentDescription = ""
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = movie.movie.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.movie_title) ,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.title,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.released_date),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.released,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.director),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.director,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.awards),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.awards,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.actors),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.actors,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.plot),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.plot,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
            }


        }
    }
}





val LazyListState.isScrolled: Boolean
    get()= firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0  //to figure out if list is scrolling or in the first origin  position



@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        DetailScreen(
            movieItem = UiMovieDetailModel(
                movie = sampleMovie1,
                isFavorite = true,
                similarMovies = sampleMovieList
            ),
            onFavoriteClick = {},
            onShareClick = {}
        )
    }
}

