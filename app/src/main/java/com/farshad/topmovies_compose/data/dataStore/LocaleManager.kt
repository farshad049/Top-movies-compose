package com.farshad.topmovies_compose.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.farshad.topmovies_compose.data.dataStore.DataStoreConstants.ENGLISH
import com.farshad.topmovies_compose.data.model.network.UserAuthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocaleManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("locale_preferences_file")
        private val locale = stringPreferencesKey("locale")
    }


    suspend fun saveLocale(selectedLocale: String) {
        context.dataStore.edit { preferences ->
            preferences[locale] = selectedLocale
        }
    }


    suspend fun deleteLocale() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    val getLocale: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[locale] ?: ENGLISH
    }
}