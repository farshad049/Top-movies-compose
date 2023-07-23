package com.farshad.topmovies_compose.data.model.network

data class UserRegisteredModel(
    val created_at: String ,
    val email: String,
    val id: Int,
    val name: String,
    val updated_at: String
)