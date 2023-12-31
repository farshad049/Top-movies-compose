package com.farshad.topmovies_compose.data.di

import android.content.Context
import com.farshad.topmovies_compose.data.remote.ApiClient
import com.farshad.topmovies_compose.data.remote.MovieService
import com.farshad.topmovies_compose.data.remote.interceptors.AuthAuthenticator
import com.farshad.topmovies_compose.data.remote.interceptors.AuthInterceptor
import com.farshad.topmovies_compose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun providesRetrofit (okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext context: Context, interceptor: AuthInterceptor, authAuthenticator: AuthAuthenticator): OkHttpClient {
        val duration = Duration.ofSeconds(10)
        return OkHttpClient.Builder()
            .connectTimeout(duration)
            .readTimeout(duration)
            .writeTimeout(duration)
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })

//            .addInterceptor(
//            ChuckerInterceptor.Builder(context)
//                .collector(ChuckerCollector(context))
//                .maxContentLength(250000L)
//                .redactHeaders(emptySet())
//                .alwaysReadResponseBody(false)
//                .build()
//        )

            .addInterceptor(interceptor)
            .authenticator(authAuthenticator)

            .build()
    }


    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }



    @Provides
    @Singleton
    fun providesApiClient(movieService: MovieService): ApiClient {
        return ApiClient(movieService)
    }








}