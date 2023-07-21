package com.farshad.topmovies_compose.ui.screnns.filter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FilterOnClicks(private val filterViewModel: FilterViewModel) {


    fun onGenreFilterClick(selectedFilter : String){
        filterViewModel.onGenreFilterClick(selectedFilter)

//        viewModel.viewModelScope.launch {
//            viewModel.filterByGenreInfoFlow.value.also { currentSelectedFilter->
//
//                val newFilter =  currentSelectedFilter.copy(
//                    selectedFilters = if(currentSelectedFilter.selectedFilters.contains(selectedFilter)){
//                        currentSelectedFilter.selectedFilters - selectedFilter
//                    }else{
//                        currentSelectedFilter.selectedFilters + selectedFilter
//                    }
//                )
//
//                viewModel._filterByGenreInfoMutableFlow.emit(newFilter)
//            }
//
//        }
    }


//    fun onImdbFilterClick(selectedFilter : String){
//        viewModel.viewModelScope.launch {
//            val currentSelectedFilter = viewModel.filterByImdbRateInfoFlow.value
//
//            val newFilter =  currentSelectedFilter.copy(
//                selectedFilters = if(currentSelectedFilter.selectedFilters.contains(selectedFilter)){
//                    currentSelectedFilter.selectedFilters - selectedFilter
//                }else{
//                    currentSelectedFilter.selectedFilters + selectedFilter
//                }
//            )
//
//            viewModel._filterByImdbRateInfoFlow.emit(newFilter)
//        }
//    }




}