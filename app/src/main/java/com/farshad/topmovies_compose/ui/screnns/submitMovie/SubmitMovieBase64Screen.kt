

package com.farshad.topmovies_compose.ui.screnns.submitMovie

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.model.domain.UploadMovieModel
import com.farshad.topmovies_compose.navigation.Screens
import com.farshad.topmovies_compose.ui.screnns.common.IntTextField
import com.farshad.topmovies_compose.ui.screnns.common.LoadingAnimation
import com.farshad.topmovies_compose.ui.screnns.common.MyButton
import com.farshad.topmovies_compose.ui.screnns.common.MyTextField
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitResponseModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.Convertors
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SubmitMovieBase64ScreenWithViewModel(
    navController: NavHostController,
    submitBase64ViewModel: SubmitBase64ViewModel= hiltViewModel(),
){
    val context= LocalContext.current
    val notificationPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    val base64Validation by submitBase64ViewModel.validationFlow.collectAsStateWithLifecycle(initialValue = SubmitFieldValidationModel())

    val submitBase64Response by submitBase64ViewModel.submitMovieBase64Flow.collectAsStateWithLifecycle(initialValue = SubmitResponseModel.Loading)


    SubmitMovieBase64Screen(
        error = base64Validation,
        onSubmitButtonClick = {
            submitBase64ViewModel.validateBase64(
                title = it.title,
                imdb_id = it.imdb_id,
                country = it.country,
                year = it.year.toString(),
                poster = it.poster
            )
        }
    )

    when(submitBase64Response){

        is SubmitResponseModel.Success ->{
            LaunchedEffect(key1 = submitBase64Response){
                val movie= (submitBase64Response as SubmitResponseModel.Success).data
                navController.navigate(Screens.Detail.passMovieID(movieId = movie.id))


                if (!notificationPermissionState.status.isGranted) {
                    notificationPermissionState.launchPermissionRequest()
                } else {
                    NotificationManager(context = context).showSimpleNotification(movieId = movie.id)
                }

            }
        }

        is SubmitResponseModel.Error -> {
            LaunchedEffect(key1 = submitBase64Response){
                Toast.makeText(context,(submitBase64Response as SubmitResponseModel.Error).message, Toast.LENGTH_LONG).show()
            }

        }

        else -> { LoadingAnimation()}
    }

}


@Composable
fun SubmitMovieBase64Screen(
    error: SubmitFieldValidationModel,
    onSubmitButtonClick: (UploadMovieModel) -> Unit,
) {

    val context= LocalContext.current

    var imageUri: Uri? by remember{ mutableStateOf(null) }
    val photoPicker= rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.PickVisualMedia()
    ){imageUri = it}

    var movieTitle by rememberSaveable { mutableStateOf("") }
    var imdbId by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("0") }
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
                imageUri = imageUri,
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
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
                                    year = year.toInt(),
                                    director = director,
                                    imdb_rating = imdbRating,
                                    imdb_votes = imdbVoting,
                                    poster = convertUriToString(context = context, uri = imageUri )
                                )
                    )
                }
            )

            Spacer(modifier = Modifier.height(56.dp))

        }
    }
}







fun convertUriToString(uri: Uri?, context: Context): String?{
    return if (uri == null){
        null
    }else{
        val bitmap= Convertors().convertUriToBitmap(context, uri)
        return Convertors().convertBitmapTOBase64(bitmap)
    }
}


@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme() {
        SubmitMovieBase64Screen(
            error =  SubmitFieldValidationModel(),
            onSubmitButtonClick = {UploadMovieModel ->},
        )
    }
}