package com.farshad.topmovies_compose.ui.screnns.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.moviesAppCompose.data.repository.MovieDetailRepository
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.data.repository.RoomRepository
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.topmovies_compose.ui.screnns.movieDetail.model.UiMovieDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _movieByIdFlow= MutableStateFlow<DomainMovieModel?>(null)
    val movieByIdFlow = _movieByIdFlow.asStateFlow()

    private val _movieByGenreFlow= MutableStateFlow<List<DomainMovieModel>>(emptyList())
    val movieByGenreFlow = _movieByGenreFlow.asStateFlow()

    private val _favoriteMovieListFlow = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val favoriteMovieListFlow = _favoriteMovieListFlow.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var movieIdForRefreshPurpose by mutableIntStateOf(0)
        private set




    fun getMovieById(movieId: Int){
        viewModelScope.launch {
            val response=repository.getMovieById(movieId)
            _movieByIdFlow.emit(response)

            if (response?.genres?.isNotEmpty() == true) {
                val genreId = genreNameToId(response.genres.component1())
                getMovieByGenre(genreId)
            }

            getFavoriteMovieList()
            movieIdForRefreshPurpose= movieId
        }
    }


    private fun  getMovieByGenre(genreId: Int){
        viewModelScope.launch {
            val response=repository.getMovieByGenre(genreId)
            _movieByGenreFlow.emit(response)
        }
    }

     private fun getFavoriteMovieList(){
        viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collectLatest {
                _favoriteMovieListFlow.emit(it)
            }
        }
    }



    val combinedData : Flow<Resource<UiMovieDetailModel>> =
        combine(
            movieByIdFlow,
            favoriteMovieListFlow ,
            movieByGenreFlow
        ){movieById , favoriteMovieList , similarMovieList ->
            if (movieById != null){
                return@combine Resource.Success(
                    UiMovieDetailModel(
                        movie = movieById,
                        isFavorite = favoriteMovieList.map { it.id }.contains(movieById.id) ,
                        similarMovies = similarMovieList
                    )
                )
            }else{
                return@combine Resource.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Resource.Loading
        )


    fun refresh(){
        viewModelScope.launch {
            _movieByIdFlow.value= null
            _movieByGenreFlow.value= emptyList()
            _movieByGenreFlow.value= emptyList()

            getMovieById(movieIdForRefreshPurpose)
            getFavoriteMovieList()

            if (combinedData.first() is Resource.Success){
                _isRefreshing.emit(false)
            }
        }
    }















    private fun genreNameToId(genreName:String?):Int{
        return when(genreName){
            "Crime" -> 1
            "Drama"-> 2
            "Action" -> 3
            "Biography" -> 4
            "History" -> 5
            "Adventure" -> 6
            "Fantasy" -> 7
            "Western" -> 8
            "Comedy" -> 9
            "Sci-Fi" -> 10
            "Mystery" -> 11
            "Thriller" -> 12
            "Family" -> 13
            "War" -> 14
            "Animation" -> 15
            "Romance" -> 16
            "Horror" -> 17
            "Music" -> 18
            "Film-Noir" -> 19
            "Musical" -> 20
            "Sport" ->21
            else -> 0
        }
    }


}