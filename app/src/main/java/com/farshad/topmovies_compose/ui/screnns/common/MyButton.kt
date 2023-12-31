package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun MyButton(
    modifier: Modifier= Modifier,
    label: String,
    isButtonLoading: Boolean = false,
    onClick: () -> Unit
){


    Button(
        modifier = modifier.fillMaxWidth().height(45.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)),
        shape = MaterialTheme.shapes.large,
        elevation = ButtonDefaults.elevation(),
        contentPadding = PaddingValues(vertical = 6.dp),
        onClick = { onClick() }
    ) {

        if (isButtonLoading){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary
            )
        }else{
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}

@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        MyButton(label = "register") {
            
        }
    }
}