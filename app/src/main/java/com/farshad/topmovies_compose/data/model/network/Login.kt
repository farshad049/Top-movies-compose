package com.farshad.moviesAppCompose.data.model.network

data class Login(
    val responseBody : UserAuthModel?,
    val errorMessage : String?
)
