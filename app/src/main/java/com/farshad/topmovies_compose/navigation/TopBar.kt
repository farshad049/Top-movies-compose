package com.farshad.topmovies_compose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.ui.theme.myRed
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun MyTopBar(
    onTopBarClick: () -> Unit,
    onDrawerClick: () -> Unit,
    isInternetConnected: Boolean
) {
    val shape = RoundedCornerShape(35.dp)

    val noInternetBackGroundColor = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.onBackground,
            Color.Gray.copy(alpha = 0.5f),
        )
    )

    val backgroundColor = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Gray.copy(alpha = 0.1f),
            Color.Transparent,
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!isInternetConnected) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(
                            topStart = 0.dp, topEnd = 0.dp, bottomStart = 35.dp, bottomEnd = 35.dp
                        ),
                        brush = noInternetBackGroundColor
                    ),
                text = stringResource(id = R.string.no_internet_connection),
                color = myRed,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                onClick = { onDrawerClick() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        shape = shape
                    )
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(
                        elevation = 8.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(35.dp)
                    )
                    .background(
                        brush = backgroundColor,
                        shape = shape
                    )
                    .clip(shape = shape)
                    .clickable { onTopBarClick() }
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.type_to_search),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

        }
    }


}


@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme() {
        MyTopBar(
            onTopBarClick = {},
            onDrawerClick = {},
            isInternetConnected = false
        )
    }

}