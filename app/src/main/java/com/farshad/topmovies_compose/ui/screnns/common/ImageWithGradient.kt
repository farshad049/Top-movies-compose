package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview


@Composable
fun ImageWithGradient(
    modifier: Modifier = Modifier,
    imageUrl: String,
    width: Dp= 80.dp,
    height: Dp= 130.dp,
    movieId: Int= 1,
    onClick: (Int)-> Unit = {}
){
    Box(modifier = modifier
        .width(width)
        .height(height),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
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
                    color = Color.Gray
                )
                .clip(MaterialTheme.shapes.medium)
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
                .clickable { onClick(movieId) },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(500)
                .error(R.drawable.error)
                .build(),
            placeholder = painterResource(id = R.drawable.place_holder) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        ImageWithGradient(
            imageUrl = ""
        )
    }
}