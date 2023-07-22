package com.farshad.topmovies_compose.ui.screnns.movieDetail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.ui.screnns.common.ImageThumbnailRow
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.movieDetail.model.UiMovieDetailModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.ui.theme.myRed
import com.farshad.topmovies_compose.ui.theme.myYellow
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleMovie1
import com.farshad.topmovies_compose.util.sampleMovieList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun DetailScreenWithViewModel(
    navController: NavHostController,
    detailViewModel: MovieDetailViewModel = hiltViewModel(),
    arg: Int?
) {
    val detailScreenOnClicks= DetailScreenOnClicks(navController = navController)
    arg?.let { detailViewModel.getMovieById(arg) }

    val data by detailViewModel.combinedData.collectAsStateWithLifecycle(initialValue = Resource.Loading)

    when (data) {
        is Resource.Success -> {
            DetailScreen(
                movieItem = (data as Resource.Success<UiMovieDetailModel>).data,
                onFavoriteClick = {},
                onShareClick = {},
                onSimilarMovieClick = {detailScreenOnClicks.onSimilarMovieClick(it)}
            )
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
    onShareClick: (Int) -> Unit,
    onSimilarMovieClick: (Int) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DetailScreeUp(
                movie = movieItem,
                onFavoriteClick = {onFavoriteClick(it)},
                onShareClick = {onShareClick(it)}
            )

            DetailScreenTexts(modifier = Modifier.padding(horizontal = 8.dp),movie = movieItem)

            Spacer(modifier = Modifier.height(16.dp))

            Images(modifier = Modifier.padding(horizontal = 8.dp),imageList = movieItem.movie.images)

            Spacer(modifier = Modifier.height(12.dp))

            SimilarMovies(
                modifier = Modifier.padding(horizontal = 8.dp),
                similarMovies = movieItem.similarMovies,
                onSimilarMovieClick = onSimilarMovieClick
            )

            Spacer(modifier = Modifier.height(12.dp))

        }

    }
}



@Composable
fun DetailScreeUp(
    modifier: Modifier= Modifier,
    movie: UiMovieDetailModel,
    onFavoriteClick: (Int)-> Unit,
    onShareClick: (Int)-> Unit
){
    Box() {
        AsyncImage(
            modifier = modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(400.dp)
                .shadow(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    ),
                    elevation = 20.dp, spotColor = MaterialTheme.colorScheme.onBackground
                )
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
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
                .data(movie.movie.poster)
                .crossfade(500)
                .error(R.drawable.error)
                .build(),
            placeholder = painterResource(id = R.drawable.place_holder) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier
                .padding(end = 8.dp, top = 10.dp)
                .align(Alignment.TopEnd)
                .background(
                    shape = RoundedCornerShape(45.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
                .padding(4.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onFavoriteClick(movie.movie.id)}) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = if (movie.isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "",
                    tint = myRed
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { onShareClick(movie.movie.id) }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Rounded.Share,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}



@Composable
fun DetailScreenTexts(
    modifier: Modifier= Modifier,
    movie: UiMovieDetailModel,
){
    Box(modifier = modifier) {
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
                                color = myYellow.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        text = " IMDB ${movie.movie.imdb_rating}/10 ",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 2.dp),
                        text = movie.movie.runTime,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(all = 2.dp),
                    text = "${stringResource(id = R.string.rated)}: ${movie.movie.rated}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp),
                    text = movie.movie.year,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
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
                    text = stringResource(id = R.string.country),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movie.movie.country,
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


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Images(
    modifier: Modifier= Modifier,
    imageList: List<String>
){
    val pagerState = rememberPagerState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary
            )
            .shadow(
                elevation = 6.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.outline
            )
            .clip(MaterialTheme.shapes.medium),
    ) {
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
        ) { page ->
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageList[page])
                    .crossfade(500)
                    .error(R.drawable.error)
                    .build(),
                placeholder = painterResource(id = R.drawable.place_holder) ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(4.dp),
            inactiveColor = MaterialTheme.colorScheme.primary,
            activeColor = MaterialTheme.colorScheme.inversePrimary
        )


    }
}


@Composable
fun SimilarMovies(
    modifier: Modifier= Modifier,
    similarMovies: List<DomainMovieModel>,
    onSimilarMovieClick: (Int) -> Unit
){
    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            text = stringResource(id = R.string.similar_movies),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        ImageThumbnailRow(
            movies = similarMovies,
            onClick = onSimilarMovieClick
        )
    }
}





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
            onShareClick = {},
            onSimilarMovieClick = {}
        )
    }
}

