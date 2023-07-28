package com.farshad.topmovies_compose.ui.screnns.filter

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.ui.screnns.filter.model.UiFilter
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview


@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title : String,
    list: List<UiFilter>,
    onFilterClick: (String) -> Unit
){
    var expandableState by remember { mutableStateOf(false)}

    val rotationState by animateFloatAsState(
        targetValue = if (expandableState) 180f else 0f
            )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .background(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
            .clip(shape = MaterialTheme.shapes.large)
            .clickable { expandableState = !expandableState },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(8f),
                    text = title ,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1 ,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = { expandableState = !expandableState } ,
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.ArrowDropDown,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Drop-down-arrow"
                    )
                }

            }
            if (expandableState){

                Spacer(modifier = Modifier.height(16.dp))

                list.forEach {
                    FilterRow(
                        uiFilter = it,
                        onFilterClick = onFilterClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}


@Composable
fun FilterRow(
    uiFilter: UiFilter,
    onFilterClick: (String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(6.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { onFilterClick(uiFilter.filterDisplayName) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = uiFilter.filterDisplayName,
            color = MaterialTheme.colorScheme.onPrimary
        )


        androidx.compose.material3.Checkbox(
            checked = uiFilter.isSelected,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkmarkColor= MaterialTheme.colorScheme.onTertiary,
            )
        )


    }
}


@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        ExpandableCard(
            title = "genre",
            list = listOf(
                UiFilter(filterDisplayName = "filter1", isSelected = false),
                UiFilter(filterDisplayName = "filter1", isSelected = true),
                UiFilter(filterDisplayName = "filter1", isSelected = true),
                UiFilter(filterDisplayName = "filter1", isSelected = false)
            ),
            onFilterClick = {}
        )


    }
}


