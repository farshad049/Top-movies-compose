package com.farshad.topmovies_compose.data.remote.interceptors

import android.content.Context
import com.farshad.topmovies_compose.data.dataStore.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext val context: Context
        ):Interceptor{

    private val tokenManager= TokenManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {

        return runBlocking(Dispatchers.IO) {
            val request = chain.request().newBuilder()

            val token = tokenManager.getAccessToken.first()
            request.addHeader("Authorization", "Bearer $token")
            request.addHeader("Accept","application/json")
            chain.proceed(request.build())
        }

    }
}