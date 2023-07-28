package com.farshad.topmovies_compose.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
) : ViewModel() {

    var genreId by mutableStateOf<Int>(1)
        private set


    fun updateSelectedGenreId(newGenreId: Int){
        genreId = newGenreId
    }



}