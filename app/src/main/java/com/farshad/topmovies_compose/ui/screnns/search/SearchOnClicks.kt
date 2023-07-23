package com.farshad.topmovies_compose.ui.screnns.search

import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.data.roomDatabase.Entity.SearchHistoryEntity
import com.farshad.topmovies_compose.navigation.Screens

class SearchOnClicks(
    private val navHostController: NavHostController,
    private val searchViewModel: SearchViewModel,
    private val searchHistoryViewModel: SearchHistoryViewModel
    ) {

    fun onMovieClick(movieId: Int,title:String){
        navHostController.navigate(Screens.Detail.passMovieID(movieId))
        searchHistoryViewModel.insertSearchHistory(
            SearchHistoryEntity(
                movieId = movieId,
                movieTitle = title
            )
        )
    }

    fun onSearchClick(query: String){
        searchViewModel.submitQuery(query)
    }
}