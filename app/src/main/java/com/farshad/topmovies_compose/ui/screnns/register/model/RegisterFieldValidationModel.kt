package com.farshad.topmovies_compose.ui.screnns.register.model



data class RegisterFieldValidationModel(
    val email: String? = null,
    val password : String? = null,
    val userName : String? = null,
    val isButtonLoading: Boolean= false
)
