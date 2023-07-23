package com.farshad.topmovies_compose.ui.screnns.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun RegisterScreenWithViewModel(
    navController: NavHostController
) {

    RegisterScreen()

}


@Composable
fun RegisterScreen() {
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
            Box() {

                RegisterHeader()


//                val errorMessage = "Text input too long"
//                var text by rememberSaveable { mutableStateOf("") }
//                var isError by rememberSaveable { mutableStateOf(false) }
//                val charLimit = 10
//
//                fun validate(text: String) {
//                    isError = text.length > charLimit
//                }
//
//                TextField(
//                    value = text,
//                    onValueChange = {
//                        text = it
//                        validate(text)
//                    },
//                    singleLine = true,
//                    isError = isError,
//                    supportingText = {
//                        if (isError) {
//                            Text(
//                                modifier = Modifier.fillMaxWidth(),
//                                text = "Limit: ${text.length}/$charLimit",
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    },
//                    trailingIcon = {
//                        if (isError)
//                            Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error)
//                    },
//                    keyboardActions = KeyboardActions { validate(text) },
//                )


            }
        }
    }
}


@Composable
fun RegisterHeader() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.register))
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            )
            .height(300.dp)
            .clickable { isPlaying = true },
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(250.dp),
            composition = composition,
            progress = { progress }
        )


    }
}


@DarkAndLightPreview
@Composable
private fun Preview() {
    RegisterScreen()
}