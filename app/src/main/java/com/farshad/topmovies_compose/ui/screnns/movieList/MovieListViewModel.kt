package com.farshad.topmovies_compose.ui.screnns.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.topmovies_compose.data.model.mapper.MovieMapper
import com.farshad.topmovies_compose.data.paging.MovieListDataSource
import com.farshad.topmovies_compose.data.remote.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
):ViewModel() {


    var movieDataSource: MovieListDataSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = MovieListDataSource(apiClient,movieMapper)
            }
            return field
        }

    val movieListFlow = Pager(
        PagingConfig(
        pageSize = 10,
        prefetchDistance = 20,
        enablePlaceholders = false
    )
    ) { movieDataSource!! }.flow.cachedIn(viewModelScope)







}