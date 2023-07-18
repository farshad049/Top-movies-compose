package com.farshad.moviesAppCompose.data.model.network

data class RegisterPostBody(
    val name:String,
    val email:String,
    val password:String
)