package com.farshad.moviesAppCompose.data.model.network

data class UserRegisteredModel(
    val created_at: String ,
    val email: String,
    val id: Int,
    val name: String,
    val updated_at: String
)