package com.farshad.topmovies_compose.ui.screnns.submitMovie.model

import com.farshad.topmovies_compose.data.model.domain.UploadMovieModel

sealed interface SubmitResponseModel {
    data class Error(val message: String) : SubmitResponseModel
    data class Success(val data: UploadMovieModel) : SubmitResponseModel
    object Loading

}