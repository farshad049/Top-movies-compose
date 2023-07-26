package com.farshad.topmovies_compose.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme_preferences_file")
        private val theme = stringPreferencesKey("theme")
    }


    suspend fun saveTheme(selectedTheme: String) {
        context.dataStore.edit { preferences ->
            preferences[theme] = selectedTheme
        }
    }


    suspend fun deleteTheme() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    val getTheme: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[theme] ?: DataStoreConstants.SYSTEM
    }


}