package com.farshad.topmovies_compose.ui.screnns.movieListByGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.moviesAppCompose.data.repository.DashboardRepository
import com.farshad.topmovies_compose.data.model.mapper.MovieMapper
import com.farshad.topmovies_compose.data.model.network.GenresModel
import com.farshad.topmovies_compose.data.paging.MovieByGenreDataSource
import com.farshad.topmovies_compose.data.remote.ApiClient
import com.farshad.topmovies_compose.ui.screnns.movieListByGenre.model.UiGenresModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieByGenreViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
    private val repository: DashboardRepository
) : ViewModel() {

    init {
        getAllGenres()
    }

    private val _allGenresMovieFlow = MutableStateFlow<List<GenresModel>>(emptyList())
    val allGenresMovieFlow = _allGenresMovieFlow.asStateFlow()

    private val _selectedGenreFlow = MutableStateFlow(2)
    val selectedGenreFlow = _selectedGenreFlow.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun getAllGenres() {
        viewModelScope.launch {
            val response = repository.getAllGenres()
            if (response.isNotEmpty()) _allGenresMovieFlow.emit(response)
        }
    }

    fun updateSelectedGenreId(genreId: Int) {
        viewModelScope.launch {
            _selectedGenreFlow.value = genreId
        }
    }


    val dataForMovieByGenreScreen: Flow<Resource<UiGenresModel>> =
        combine(
            allGenresMovieFlow,
            selectedGenreFlow
        ) { allGenres, selectedGenreId ->
            val combinedData =
                if (allGenres.isNotEmpty()) {
                    Resource.Success(
                        UiGenresModel(
                            genreList = allGenres.map {
                                UiGenresModel.GenreWithFavorite(
                                    genre = it,
                                    isSelected = it.id == selectedGenreId
                                )
                            }
                        )
                    )
                } else {
                    Resource.Loading
                }
            return@combine combinedData
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Resource.Loading
        )


    private var genreId: Int = 0
    var pagingSource: MovieByGenreDataSource? = null
        get() {
            if (field == null || field?.invalid == true) {
                field = MovieByGenreDataSource(apiClient, movieMapper, genreId = genreId)
            }
            return field
        }

    val movieByGenreFlow = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 20,
            enablePlaceholders = false
        )
    ) { pagingSource!! }.flow.cachedIn(viewModelScope)


    fun submitQuery(genreIdFromScreen: Int) {
        genreId = genreIdFromScreen
        pagingSource?.invalidate()

        viewModelScope.launch {
            _selectedGenreFlow.value = genreIdFromScreen
        }
    }


    fun refresh() {
        viewModelScope.launch {
            pagingSource?.invalidate()
            _isRefreshing.emit(false)
        }
    }


}