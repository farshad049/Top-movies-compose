package com.farshad.topmovies_compose.data.dataStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.model.network.UserAuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val tokenManager: TokenManager1
): ViewModel() {

    val token= tokenManager.getAccessToken

    val refreshToken= tokenManager.getRefreshToken

    val isLoggedIn= tokenManager.getIsLoggedIn

    fun saveToken(token: UserAuthModel)= viewModelScope.launch {
        tokenManager.saveToken(token)
    }

    fun deleteToken() = viewModelScope.launch {
        tokenManager.deleteToken()
    }




}