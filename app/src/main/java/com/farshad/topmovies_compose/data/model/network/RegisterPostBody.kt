package com.farshad.topmovies_compose.data.model.network

data class RegisterPostBody(
    val name:String,
    val email:String,
    val password:String
)