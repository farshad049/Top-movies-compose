@file:JvmName("SubmitMovieKt")

package com.farshad.topmovies_compose.ui.screnns.submitMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.screnns.common.IntTextField
import com.farshad.topmovies_compose.ui.screnns.common.MyButton
import com.farshad.topmovies_compose.ui.screnns.common.MyTextField
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.UploadMovieModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun SubmitMovieBase64(
    error: SubmitFieldValidationModel,
    onSubmitButtonClick: (UploadMovieModel) -> Unit,
    onImageClick: () -> Unit
) {

    var movieTitle by rememberSaveable { mutableStateOf("") }
    var imdbId by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableIntStateOf(0) }
    var director by rememberSaveable { mutableStateOf("") }
    var imdbRating by rememberSaveable { mutableStateOf("") }
    var imdbVoting by rememberSaveable { mutableStateOf("") }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(28.dp))

            SubmitImage(
                imageUrl = "",
                onClick = onImageClick
            )

            Spacer(modifier = Modifier.height(16.dp))


            MyTextField(
                error = error.title,
                label = stringResource(id = R.string.movie_title),
                valueOfTxtField = { movieTitle = it }
            )

            MyTextField(
                error = error.imdbId,
                label = stringResource(id = R.string.imdb_id),
                valueOfTxtField = { imdbId = it }
            )

            MyTextField(
                error = error.country,
                label = stringResource(id = R.string.country),
                valueOfTxtField = { country = it }
            )

            IntTextField(
                error = error.year,
                label = stringResource(id = R.string.year),
                valueOfTxtField = { year = it }
            )

            MyTextField(
                label = stringResource(id = R.string.director),
                valueOfTxtField = { director = it }
            )

            MyTextField(
                label = stringResource(id = R.string.imdb_rating),
                valueOfTxtField = { imdbRating = it }
            )

            MyTextField(
                label = stringResource(id = R.string.imdb_voting),
                valueOfTxtField = { imdbVoting = it }
            )

            Spacer(modifier = Modifier.height(16.dp))


            MyButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.6f),
                label = stringResource(id = R.string.upload_base_64),
                onClick = {
                    onSubmitButtonClick(
                                UploadMovieModel(
                                    title = movieTitle,
                                    imdb_id = imdbId,
                                    country = country,
                                    year = year,
                                    director = director,
                                    imdb_rating = imdbRating,
                                    imdb_votes = imdbVoting
                                )
                    )
                }
            )

        }
    }
}


@Composable
fun SubmitImage(
    modifier: Modifier = Modifier,
    imageUrl: String ="",
    width: Dp = 200.dp,
    height: Dp = 200.dp,
    onClick: ()-> Unit
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
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(500)
                .error(R.drawable.error)
                .build(),
            placeholder = painterResource(id = R.drawable.place_holder) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            onClick = { onClick() }
        ) {
            Icon(
                modifier = Modifier.size(130.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}



@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme() {
        SubmitMovieBase64(
            error =  SubmitFieldValidationModel(),
            onSubmitButtonClick = {a,b,c,d,e,f,g ->},
            onImageClick = {}
        )
    }
}