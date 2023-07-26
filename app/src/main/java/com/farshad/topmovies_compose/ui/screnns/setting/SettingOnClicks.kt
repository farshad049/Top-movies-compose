package com.farshad.topmovies_compose.ui.screnns.setting

import com.farshad.topmovies_compose.data.dataStore.DataStoreViewModel

class SettingOnClicks(private val dataStoreViewModel: DataStoreViewModel) {

    fun onThemeClick(newTheme: String){
        dataStoreViewModel.saveTheme(newTheme)
    }

    fun onLocaleChangeClick(newLocale: String){
        dataStoreViewModel.saveLocale(newLocale)
    }

}