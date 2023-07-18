package com.farshad.topmovies_compose.ui.screnns.movieDetail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farshad.moviesAppCompose.data.model.domain.DomainMovieModel
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.common.MovieHorizontalItemShimmerLst
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleMovie1

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
    movieItem: DomainMovieModel
) {

    val lazyListState= rememberLazyListState()
    val height= 240.dp
    val animationDuration= 500

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(animationSpec = tween(durationMillis = animationDuration))
                    .height(height = if (lazyListState.isScrolled) 0.dp else height)
                    .shadow(
                        elevation = 6.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
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
                    }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(animationSpec = tween(durationMillis = animationDuration))
                        .height(height= if (lazyListState.isScrolled) 0.dp else height),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("painterResource(id = R.drawable.image)")
                        .crossfade(500)
                        .error(R.drawable.error)
                        .build(),
                    placeholder = painterResource(id = R.drawable.place_holder) ,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

        }
    }


    val padding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else height,
        animationSpec = tween(durationMillis = animationDuration)
    )

    LazyColumn(
        modifier = Modifier.padding(top = padding),
        state = lazyListState
    ) {
        item {
            MovieHorizontalItemShimmerLst()
        }

    }

}






val LazyListState.isScrolled: Boolean
    get()= firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0  //to figure out if list is scrolling or in the first origin  position


@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        DetailScreen(movieItem = sampleMovie1)
    }
}

