package com.farshad.topmovies_compose.data.di

import android.content.Context
import com.farshad.topmovies_compose.data.dataStore.TokenManager1
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
    fun provideDataStore(@ApplicationContext context: Context):TokenManager1{
        return TokenManager1(context = context)
    }


}