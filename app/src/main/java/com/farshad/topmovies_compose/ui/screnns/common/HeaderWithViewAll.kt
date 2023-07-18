package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview


@Composable
fun HeaderWithViewAll(
    modifier: Modifier = Modifier,
    title: String,
    onViewAllClick: () -> Unit
){
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.clickable { onViewAllClick() },
                text = stringResource(id = R.string.View_all),
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}


@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        HeaderWithViewAll(title = "action" ) {null}
    }
}