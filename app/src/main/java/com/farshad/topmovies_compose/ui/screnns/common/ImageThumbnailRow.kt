package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel

@Composable
fun ImageThumbnailRow(
    modifier: Modifier = Modifier,
    movies: List<DomainMovieModel>,
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){

        itemsIndexed(
            items = movies, key = { index, item -> item.hashCode()}
        ){index, item->
            ListItem(
                movieId = item.id,
                imageUrl = item.poster,
                onClick = onClick
            )


        }
    }

}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    movieId: Int,
    imageUrl: String,
    onClick: (Int)->Unit
) {

    ImageWithGradient(
        modifier= modifier,
        width = 130.dp,
        height = 180.dp,
        movieId = movieId,
        imageUrl = imageUrl,
        onClick = onClick
    )

}
