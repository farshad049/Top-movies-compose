package com.farshad.topmovies_compose.ui.screnns.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.navigation.SharedViewModel
import com.farshad.topmovies_compose.ui.screnns.filter.model.DataForFilterScreen
import com.farshad.topmovies_compose.ui.screnns.filter.model.FilterAndSelectedFilterList

@Composable
fun FilterScreenWithViewModel(
    navController: NavHostController,
    filterViewModel: FilterViewModel
) {
    val filterOnClicks = FilterOnClicks(filterViewModel = filterViewModel)

    val a by filterViewModel.combinedDataForFilterScreen.collectAsStateWithLifecycle(initialValue = DataForFilterScreen())

    FilterScreen(
        dataForFilterScreen = a,
        onFilterClick = {filterOnClicks.onGenreFilterClick(it)}
    )
}

@Composable
fun FilterScreen(
    dataForFilterScreen: DataForFilterScreen,
    onFilterClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ExpandableCard(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = stringResource(id = R.string.genres),
                list = dataForFilterScreen.filteredByGenreList,
                onFilterClick = {onFilterClick(it)}
            )


        }
    }
}
