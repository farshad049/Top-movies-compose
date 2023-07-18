package com.farshad.topmovies_compose.ui.screnns.dashboard

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.ui.screnns.dashboard.model.DashboardUiModel
import com.farshad.topmovies_compose.ui.screnns.common.ImageWithGradient
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.SampleDashboardModel


@Composable
fun DashboardImageThumbnailRow(
    modifier: Modifier = Modifier,
    movies: List<DomainMovieModel>,
    onClick: (Int) -> Unit
){

    val widthOfScreen= LocalConfiguration.current.screenHeightDp / 2
    val listState = rememberLazyListState(initialFirstVisibleItemScrollOffset = widthOfScreen)


    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        state = listState
    ){

        itemsIndexed(
            items = movies, key = { index, item -> item.hashCode()}
        ){index, item->
            ListItem(
                listState = listState,
                index = index,
                imageUrl = item.poster,
                movieId = item.id,
                onClick = onClick
            )
        }
    }
}



@Composable
private fun ListItem(
    modifier: Modifier= Modifier,
    listState: LazyListState,
    index: Int,
    movieId: Int,
    imageUrl: String,
    onClick: (Int)->Unit
) {

    val isInCenter by remember {
        derivedStateOf {

            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            val itemInfo = visibleItemsInfo.firstOrNull { it.index == index}

            itemInfo?.let {

                val delta = it.size/2 //use your custom logic
                val center = listState.layoutInfo.viewportEndOffset / 2
                val childCenter = it.offset + it.size / 2
                val target = childCenter - center
                if (target in -delta..delta) return@derivedStateOf true
            }
            false
        }
    }

    val width by animateDpAsState(targetValue = if (isInCenter) (210*1.1).dp else 210.dp)
    val height by animateDpAsState(targetValue = if (isInCenter) (280*1.1).dp else 280.dp)

    ImageWithGradient(
        modifier= modifier,
        width = width,
        height = height,
        movieId = movieId,
        imageUrl = imageUrl,
        onClick = onClick
    )

}






@DarkAndLightPreview
@Composable
private fun Preview(@PreviewParameter(SampleDashboardModel::class) movie: DashboardUiModel){
    AppTheme() {
        DashboardImageThumbnailRow(
            movies = movie.randomMovies,
            onClick = {}
        )
    }
}


