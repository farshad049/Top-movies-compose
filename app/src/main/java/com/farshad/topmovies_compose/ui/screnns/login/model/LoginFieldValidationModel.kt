package com.farshad.topmovies_compose.ui.screnns.login.model

data class LoginFieldValidationModel(
    val email: String? = null,
    val password : String? = null,
    val buttonLoading: Boolean= false
)
