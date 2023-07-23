package com.farshad.topmovies_compose.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.farshad.topmovies_compose.data.model.network.UserAuthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager1(private val context: Context) {


    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token_preferences_file")
        private val token = stringPreferencesKey("token")
        private val refreshToken = stringPreferencesKey("refresh_token")
        private val isLoggedIn = booleanPreferencesKey("is_logged_in")
    }




    suspend fun saveToken(userToken: UserAuthModel?) {

        userToken?.let {
            context.dataStore.edit { preferences ->
                preferences[token] = userToken.access_token
                preferences[refreshToken] = userToken.refresh_token
                preferences[isLoggedIn] = true
            }
        }

    }


    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[token] ?: ""
    }


    val getRefreshToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[refreshToken] ?: ""
    }

    val getIsLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[isLoggedIn] ?: false
    }



}