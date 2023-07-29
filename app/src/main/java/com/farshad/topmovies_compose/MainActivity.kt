package com.farshad.topmovies_compose

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farshad.topmovies_compose.data.dataStore.DataStoreViewModel
import com.farshad.topmovies_compose.navigation.MainScreen
import com.farshad.topmovies_compose.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navHostController: NavHostController

    private val dataStoreViewModel: DataStoreViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen()


        lifecycleScope.launch {
            dataStoreViewModel.locale.collectLatest {
                setLocale(languageCode = it)
            }
        }





        setContent {
            AppTheme() {
                navHostController = rememberNavController()
                MainScreen(navHostController = navHostController)
            }
        }


    }


    //set locale
    private fun setLocale(activity: Activity = this, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}



