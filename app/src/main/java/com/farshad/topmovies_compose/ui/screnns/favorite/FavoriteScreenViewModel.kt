package com.farshad.topmovies_compose.ui.screnns.favorite

import androidx.lifecycle.*
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.data.repository.FavoriteMovieRepository
import com.farshad.topmovies_compose.data.repository.RoomRepository
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {


    private val _insertMovieFlow = MutableStateFlow(false)
    val insertMovieFlow= _insertMovieFlow.asStateFlow()

    private val _deleteMovieFlow = MutableStateFlow(false)
    val deleteMovieFlow = _deleteMovieFlow.asStateFlow()

    private val _listOfFavoriteMovie= MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val listOfFavoriteMovie= _listOfFavoriteMovie.asStateFlow()


    fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            roomRepository.insertFavoriteMovie(movie)
            _insertMovieFlow.emit(true)
        }
    }

    fun deleteFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            roomRepository.deleteFavoriteMovie(movie)
            _deleteMovieFlow.emit(true)
        }
    }

     fun getFavoriteMovieList()= viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collect {list->
                if (list.isNotEmpty()){
                    _listOfFavoriteMovie.emit(list)
                }
            }
        }









}