package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farshad.moviesAppCompose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.ui.theme.myYellow
import com.farshad.topmovies_compose.util.Convertors
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleMovie1


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieHorizontalLazyColumn(
    modifier: Modifier= Modifier,
    movieList: LazyPagingItems<DomainMovieModel>,
    onRowClick: (Int)-> Unit,
){
    val listForRow by remember { mutableStateOf(movieList) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ){
        items(
            items = listForRow, key = {it.id}
        ){
            it?.let {movie->
                MovieHorizontalItem(
                    modifier = Modifier.animateItemPlacement(),
                    movie = movie,
                    onRowClick = {onRowClick(it)}
                )
            }
        }
    }
}



@Composable
fun MovieHorizontalItem(
    modifier: Modifier = Modifier,
    movie: DomainMovieModel,
    onRowClick: (Int)-> Unit,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .shadow(
                elevation = 3.dp,
                spotColor = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { onRowClick(movie.id) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1.1f)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.poster)
                        .crossfade(500)
                        .error(R.drawable.error)
                        .build(),
                    placeholder = painterResource(id = R.drawable.place_holder) ,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .weight(3f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Column() {
                    Text(
                        text = movie.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = Convertors().convertListToText(movie.genres),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        tint = myYellow,
                        contentDescription =""
                    )
                    Text(
                        text = movie.imdb_rating,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.castle),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        contentDescription =""
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.country,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                }
            }
        }

    }

}





@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        MovieHorizontalItem(
            movie = sampleMovie1,
            onRowClick = {}
        )
    }
}

