package com.farshad.topmovies_compose.ui.screnns.search

class SearchHistoryOnClicks(
    private val searchHistoryViewModel: SearchHistoryViewModel
) {

    fun onDeleteAllMovieClick(){
        searchHistoryViewModel.deleteAllSearchHistory()
    }

    fun onDeleteMovieItemClick(movieId: Int){
        searchHistoryViewModel.deleteSearchHistoryByID(movieId)
    }
}