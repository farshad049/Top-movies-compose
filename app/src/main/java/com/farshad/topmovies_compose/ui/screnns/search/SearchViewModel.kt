package com.farshad.topmovies_compose.ui.screnns.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.topmovies_compose.data.model.mapper.MovieMapper
import com.farshad.topmovies_compose.data.paging.SearchDataSource
import com.farshad.topmovies_compose.data.remote.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper
    ):ViewModel() {

    var userSearch by mutableStateOf<String>("")
        private set

    fun updateUserSearch(newUserSearch: String){
        userSearch = newUserSearch
    }


   // private var currentUserSearch:String=""

    private var pagingSource: SearchDataSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = SearchDataSource(apiClient,movieMapper,userSearch = userSearch)
            }
            return field
        }

    val searchFlow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 40,
            enablePlaceholders = false
        )
    ) { pagingSource!! }.flow.cachedIn(viewModelScope)





    fun submitQuery(userSearch:String){
        updateUserSearch(userSearch)
       // currentUserSearch=userSearch
        pagingSource?.invalidate()

    }

}