package com.farshad.topmovies_compose.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BiometricManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("biometric_preferences_file")
        private val biometric = booleanPreferencesKey("biometric")
    }


    suspend fun saveBiometric(selectedBiometric: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[biometric] = selectedBiometric
        }
    }


    suspend fun deleteBiometric() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    val getBiometric: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[biometric] ?: false
    }
}