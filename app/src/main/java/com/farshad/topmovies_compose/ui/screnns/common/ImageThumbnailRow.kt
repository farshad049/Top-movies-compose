package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleMovie1

@Composable
fun ImageThumbnailRow(
    modifier: Modifier = Modifier,
    movies: List<DomainMovieModel>,
    onClick: (Int) -> Unit
) {
    val listState= rememberLazyListState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        state = listState
    ){

        itemsIndexed(
            items = movies, key = { index, item -> item.hashCode()}
        ){index, item->
            ListItem(
                movie = item,
                onClick = onClick
            )


        }
    }

}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    movie: DomainMovieModel,
    onClick: (Int)->Unit
) {
    Column(
        modifier = modifier
            .width(130.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageWithGradient(
            modifier= modifier,
            width = 130.dp,
            height = 180.dp,
            movieId = movie.id,
            imageUrl = movie.poster,
            onClick = {onClick(it)}
        )

        Text(
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

@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        ListItem(
            movie = sampleMovie1,
            onClick = {}
        )
    }
}


