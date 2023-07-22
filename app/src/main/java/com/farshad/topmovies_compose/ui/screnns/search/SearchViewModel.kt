package com.farshad.topmovies_compose.ui.screnns.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.topmovies_compose.data.model.mapper.MovieMapper
import com.farshad.topmovies_compose.data.paging.SearchDataSource
import com.farshad.topmovies_compose.data.remote.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper
    ):ViewModel() {



    private var currentUserSearch:String=""

    private var pagingSource: SearchDataSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = SearchDataSource(apiClient,movieMapper,userSearch = currentUserSearch)
                { viewModelScope.launch { _localExceptionFlow.send(it) } }
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


    private val _localExceptionFlow = Channel<SearchDataSource.LocalException>()
    val localExceptionFlow= _localExceptionFlow.receiveAsFlow()



    fun submitQuery(userSearch:String){
        currentUserSearch=userSearch
        pagingSource?.invalidate()

    }

}