package com.farshad.topmovies_compose.data.di

import android.content.Context
import com.farshad.topmovies_compose.data.dataStore.BiometricManager
import com.farshad.topmovies_compose.data.dataStore.LocaleManager
import com.farshad.topmovies_compose.data.dataStore.ThemeManager
import com.farshad.topmovies_compose.data.dataStore.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context):TokenManager{
        return TokenManager(context = context)
    }

    @Provides
    @Singleton
    fun provideLocaleManager(@ApplicationContext context: Context):LocaleManager{
        return LocaleManager(context = context)
    }


    @Provides
    @Singleton
    fun provideBiometricManager(@ApplicationContext context: Context): BiometricManager {
        return BiometricManager(context = context)
    }

    @Provides
    @Singleton
    fun provideThemeManager(@ApplicationContext context: Context): ThemeManager {
        return ThemeManager(context = context)
    }




}