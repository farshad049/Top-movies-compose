package com.farshad.topmovies_compose.ui.screnns.login.model

data class LoginUserModel(
    val userName: TextFieldStatusModel = TextFieldStatusModel.Success(),
    val password : TextFieldStatusModel = TextFieldStatusModel.Success(),
)
