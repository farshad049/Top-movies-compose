package com.farshad.topmovies_compose.data.model.network


data class Login(
    val responseBody : UserAuthModel?,
    val errorMessage : String?
)
