package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farshad.topmovies_compose.data.model.network.GenresModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.farshad.topmovies_compose.util.sampleGenreList


@Composable
fun SuggestionChipLazyRow(
    modifier: Modifier= Modifier,
    list: List<GenresModel>,
    onClick: (Int)-> Unit
){
    val listForRow by remember { mutableStateOf(list) }
    val listState= rememberLazyListState()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ){
       items(
           items = listForRow, key = {it.hashCode()}
       ){item->
           ChipSuggestionItem(
               genreItem = item,
               onChipClick = {onClick(it)}
           )
       }
    }
}


@Composable
fun ChipSuggestionItem(
    genreItem: GenresModel,
    onChipClick: (Int)-> Unit
){
    SuggestionChip(
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = null,
        label = { Text(text = genreItem.name) } ,
        onClick = { onChipClick(genreItem.id) },
        elevation = SuggestionChipDefaults.suggestionChipElevation(
            elevation = 3.dp
        )
    )
}



@DarkAndLightPreview
@Composable
private fun Preview()
{
    AppTheme() {
        SuggestionChipLazyRow(
            list = sampleGenreList,
            onClick = {}
        )
    }
}