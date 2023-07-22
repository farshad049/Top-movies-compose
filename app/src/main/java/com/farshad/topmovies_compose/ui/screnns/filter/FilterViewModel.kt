package com.farshad.topmovies_compose.ui.screnns.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.ui.screnns.filter.model.DataForFilterScreen
import com.farshad.topmovies_compose.ui.screnns.filter.model.FilterAndSelectedFilterList
import com.farshad.topmovies_compose.ui.screnns.filter.model.ModelDataForMovieList
import com.farshad.topmovies_compose.ui.screnns.filter.model.UiFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(): ViewModel() {


    val _filterByGenreInfoMutableFlow = MutableStateFlow(
        FilterAndSelectedFilterList(
            filters = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
            selectedFilters = emptySet()
        )
    )

    val filterByGenreInfoFlow = _filterByGenreInfoMutableFlow.asStateFlow()




    val _filterByImdbRateInfoFlow = MutableStateFlow(
        FilterAndSelectedFilterList(
            filters =  setOf( "9.0" , "8.5", "8.0" ),
            selectedFilters = emptySet()
        )
    )

    val filterByImdbRateInfoFlow = _filterByImdbRateInfoFlow.asStateFlow()




    fun onGenreFilterClick(selectedFilter : String){
        viewModelScope.launch {
            filterByGenreInfoFlow.value.also { currentSelectedFilter->

                val newFilter =  currentSelectedFilter.copy(
                    selectedFilters = if(currentSelectedFilter.selectedFilters.contains(selectedFilter)){
                        currentSelectedFilter.selectedFilters - selectedFilter
                    }else{
                        currentSelectedFilter.selectedFilters + selectedFilter
                    }
                )
                _filterByGenreInfoMutableFlow.emit(newFilter)
            }
        }
    }

    fun onImdbFilterClick(selectedFilter : String){
        viewModelScope.launch {
            filterByImdbRateInfoFlow.value.also { currentSelectedFilter->

                val newFilter =  currentSelectedFilter.copy(
                    selectedFilters = if(currentSelectedFilter.selectedFilters.contains(selectedFilter)){
                        currentSelectedFilter.selectedFilters - selectedFilter
                    }else{
                        currentSelectedFilter.selectedFilters + selectedFilter
                    }
                )
                _filterByImdbRateInfoFlow.emit(newFilter)
            }
        }
    }

    fun onMovieListFilterRowItemClick(filterToRemove : String){
        viewModelScope.launch {
            when{

                filterByGenreInfoFlow.value.filters.contains(filterToRemove) -> {
                        val currentSelectedFilter = filterByGenreInfoFlow.value

                        val newFilter =  currentSelectedFilter.copy(
                            selectedFilters = if(currentSelectedFilter.selectedFilters.contains(filterToRemove)){
                                currentSelectedFilter.selectedFilters - filterToRemove
                            }else{
                                currentSelectedFilter.selectedFilters
                            }
                        )
                        _filterByGenreInfoMutableFlow.emit(newFilter)
                }


                filterByImdbRateInfoFlow.value.filters.contains(filterToRemove) -> {
                        val currentSelectedFilter = filterByImdbRateInfoFlow.value

                        val newFilter =  currentSelectedFilter.copy(
                            selectedFilters = if(currentSelectedFilter.selectedFilters.contains(filterToRemove)){
                                currentSelectedFilter.selectedFilters - filterToRemove
                            }else{
                                currentSelectedFilter.selectedFilters
                            }
                        )
                        _filterByImdbRateInfoFlow.emit(newFilter)
                }



            }
        }
    }






    val combinedDataForFilterScreen =
        combine(
            filterByGenreInfoFlow ,
            filterByImdbRateInfoFlow
        ) { setOfGenresFilter, setOfImdbFilter->

            val genreData : List<UiFilter> =
               setOfGenresFilter.filters.map { genres ->
                    UiFilter(
                        filterDisplayName = genres,
                        isSelected = setOfGenresFilter.selectedFilters.contains(genres)
                    )
                }


            val imdbData: List<UiFilter> =
                setOfImdbFilter.filters.map { imdbRate ->
                    UiFilter(
                        filterDisplayName = imdbRate,
                        isSelected = setOfImdbFilter.selectedFilters.contains(imdbRate)
                    )
                }


            return@combine DataForFilterScreen(genreData , imdbData)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = DataForFilterScreen()
            )





    val combinedDataForFilterRowMovieList =
        combine(
            filterByGenreInfoFlow ,
            filterByImdbRateInfoFlow
        ){genreSelectedFilters , imdbRateSelectedFilters ->
            genreSelectedFilters.selectedFilters + imdbRateSelectedFilters.selectedFilters
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptySet()
        )





    val combinedFilterDataForMovieList =
        combine(
            filterByGenreInfoFlow ,
            filterByImdbRateInfoFlow
        ){genreSetOfSelectedFilters , imdbSetOfSelectedFilters ->
            ModelDataForMovieList(
                genreSetOfSelectedFilters = genreSetOfSelectedFilters.selectedFilters ,
                imdbSetOfSelectedFilters = imdbSetOfSelectedFilters.selectedFilters
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ModelDataForMovieList()
        )

}