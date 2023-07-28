package com.farshad.topmovies_compose.ui.screnns.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.repository.RoomRepository
import com.farshad.topmovies_compose.data.roomDatabase.Entity.SearchHistoryEntity
import com.farshad.topmovies_compose.data.roomDatabase.Entity.SearchHistoryEntityWithoutId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    init {
        getSearchHistoryList()
    }

    private val _searchHistoryListMutableFlow = MutableStateFlow<List<SearchHistoryEntityWithoutId>>(emptyList())
    val searchHistoryListFlow= _searchHistoryListMutableFlow.asStateFlow()

    private val _insertSearchHistoryMutableFlow = MutableStateFlow<Boolean>(false)
    val insertSearchHistoryFlow= _insertSearchHistoryMutableFlow.asStateFlow()

    private val _deleteSearchHistoryMutableFlow = MutableStateFlow<Boolean>(false)
    val deleteSearchHistoryFlow= _deleteSearchHistoryMutableFlow.asStateFlow()

    private val _deleteAllSearchHistoryByIDMutableFlow = MutableStateFlow<Boolean>(false)
    val deleteAllSearchHistoryByIdFlow= _deleteAllSearchHistoryByIDMutableFlow.asStateFlow()


    fun insertSearchHistory(movie : SearchHistoryEntity){
        viewModelScope.launch {
            repository.insertMovieSearchHistory(movie)
            _insertSearchHistoryMutableFlow.emit(true)
        }
    }

    fun deleteAllSearchHistory(){
        viewModelScope.launch {
            repository.deleteAllMovieSearchHistory()
            _deleteSearchHistoryMutableFlow.emit(true)
        }
    }

    fun deleteSearchHistoryByID(movieId:Int){
        viewModelScope.launch {
            repository.deleteMovieSearchById(movieId)
            _deleteAllSearchHistoryByIDMutableFlow.emit(true)
        }
    }

    fun getSearchHistoryList(){
        viewModelScope.launch {
            repository.getAllMovieSearchHistory().collectLatest {
                _searchHistoryListMutableFlow.emit(it)
            }
        }
    }




}