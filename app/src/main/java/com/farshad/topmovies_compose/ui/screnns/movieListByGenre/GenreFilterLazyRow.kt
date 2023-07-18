package com.farshad.topmovies_compose.ui.screnns.movieListByGenre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.farshad.moviesAppCompose.data.model.network.GenresModel
import com.farshad.topmovies_compose.ui.screnns.movieListByGenre.model.UiGenresModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview


@Composable
fun GenreFilterLazyRow(
    modifier: Modifier= Modifier,
    list: List<UiGenresModel.GenreWithFavorite>,
    onClick: (Int)-> Unit
){
    val listForRow by remember { mutableStateOf(list) }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(
            items = list, key = {it.hashCode()}
        ){item->
            GenreFilterItem(
                uiGenresModel = item,
                onClick = {onClick(it)}
                )
        }
    }
}





@Composable
fun GenreFilterItem(
    uiGenresModel: UiGenresModel.GenreWithFavorite,
    onClick: (Int) -> Unit
){
    SuggestionChip(
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (uiGenresModel.isSelected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        border = null,
        label = { Text(text = uiGenresModel.genre.name) } ,
        onClick = { onClick(uiGenresModel.genre.id) },
    )

}







@DarkAndLightPreview
@Composable
private fun Preview(){
    AppTheme() {
        Column() {
            GenreFilterItem(
                uiGenresModel = UiGenresModel.GenreWithFavorite(genre = GenresModel(id = 1, name = "comedy"),isSelected = false),
                onClick = {}
            )

        }
    }
}