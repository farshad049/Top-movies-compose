package com.farshad.topmovies_compose.data.dataStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.model.network.UserAuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val localeManager: LocaleManager,
    private val biometricManager: BiometricManager,
    private val themeManager: ThemeManager
): ViewModel() {

    val token= tokenManager.getAccessToken
    val refreshToken= tokenManager.getRefreshToken
    val isLoggedIn= tokenManager.getIsLoggedIn
    fun saveToken(newToken: UserAuthModel)= viewModelScope.launch {
        tokenManager.saveToken(newToken)
    }
    fun deleteToken() = viewModelScope.launch {
        tokenManager.deleteToken()
    }






    val locale= localeManager.getLocale
    fun saveLocale(newLocale: String)= viewModelScope.launch {
        localeManager.saveLocale(newLocale)
    }





    val theme= themeManager.getTheme
    fun saveTheme(newTheme: String)= viewModelScope.launch {
        themeManager.saveTheme(newTheme)
    }





    val biometric= biometricManager.getBiometric
    fun saveBiometric(newBiometric: Boolean)= viewModelScope.launch {
        biometricManager.saveBiometric(newBiometric)
    }







}