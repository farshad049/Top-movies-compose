package com.farshad.topmovies_compose.ui.screnns.submitMovie

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.IconButton
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

@Composable
fun SubmitImage(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    width: Dp = 200.dp,
    height: Dp = 200.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
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
                .clickable { onClick() },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(500)
                .error(R.drawable.place_holder)
                .build(),
            placeholder = painterResource(id = R.drawable.place_holder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        if (imageUri == null) {

            Icon(
                modifier = Modifier.size(130.dp).align(Alignment.Center),
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }


    }
}