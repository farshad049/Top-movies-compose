package com.farshad.topmovies_compose.ui.screnns.submitMovie.model

sealed interface SubmitResponseModel {
    data class Error(val message: String) : SubmitResponseModel
    data class Success(val data: UploadMovieModel) : SubmitResponseModel
    object Loading

}