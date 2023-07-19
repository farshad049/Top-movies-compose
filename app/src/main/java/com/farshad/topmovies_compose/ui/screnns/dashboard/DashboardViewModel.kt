package com.farshad.topmovies_compose.ui.screnns.dashboard

import androidx.lifecycle.*
import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.data.model.network.GenresModel
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.moviesAppCompose.data.repository.DashboardRepository
import com.farshad.topmovies_compose.ui.screnns.dashboard.model.DashboardUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    init {
        getFirstAndSecondMoviePage()
        getAllGenres()
    }

    private val _firstPageMovieFlow = MutableStateFlow<Resource<List<DomainMovieModel>>>(Resource.Loading)
    val firstPageMovieFlow= _firstPageMovieFlow.asStateFlow()

    private val _allGenresMovieFlow= MutableStateFlow<Resource<List<GenresModel>>>(Resource.Loading)
    val allGenresMovieFlow = _allGenresMovieFlow.asStateFlow()


    fun getFirstAndSecondMoviePage(){
        viewModelScope.launch {
            val first10Movies= repository.getMovieByPage(1)
            val second10Movies= repository.getMovieByPage(2)
            val response = first10Movies + second10Movies
            if (response.isNotEmpty()) _firstPageMovieFlow.emit(Resource.Success(response))
        }
    }

    fun getAllGenres(){
        viewModelScope.launch {
            val response= repository.getAllGenres()
            if (response.isNotEmpty()) _allGenresMovieFlow.emit(Resource.Success(response))
        }
    }


   val combinedData : Flow<Resource<DashboardUiModel>> =
          combine(
             firstPageMovieFlow,
             allGenresMovieFlow
         ){listOfMovie , listOfGenre ->
              val genreAndMovie=
             if (listOfMovie is Resource.Success && listOfGenre is Resource.Success){
                 val randomMovie = listOfMovie.data.shuffled().take(5)
                  Resource.Success(
                     DashboardUiModel(
                         movie = listOfMovie.data,
                         genre = listOfGenre.data,
                         randomMovies = randomMovie
                     )
                  )
             }else{
                 Resource.Loading
             }
              return@combine genreAndMovie
         }.stateIn(
             scope = viewModelScope,
             started = SharingStarted.WhileSubscribed(),
             initialValue = Resource.Loading
         )
    }








