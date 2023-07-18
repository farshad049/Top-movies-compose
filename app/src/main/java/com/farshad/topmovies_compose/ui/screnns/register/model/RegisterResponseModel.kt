package com.farshad.topmovies_compose.ui.screnns.register.model

import com.farshad.moviesAppCompose.data.model.network.UserRegisteredModel

sealed interface RegisterResponseModel {
    data class Success(val data: UserRegisteredModel?) : RegisterResponseModel
    data class Error(val error: String?) : RegisterResponseModel
}