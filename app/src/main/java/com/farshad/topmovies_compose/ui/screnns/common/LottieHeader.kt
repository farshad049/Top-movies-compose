package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieHeader(
    lottieCompositionSpec: LottieCompositionSpec,
    modifier: Modifier = Modifier,
    backgroundColor: Color= MaterialTheme.colorScheme.surface
) {
    val composition by rememberLottieComposition(spec = lottieCompositionSpec)
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }


    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium
            )
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(250.dp),
            composition = composition,
            progress = { progress }
        )


    }
}