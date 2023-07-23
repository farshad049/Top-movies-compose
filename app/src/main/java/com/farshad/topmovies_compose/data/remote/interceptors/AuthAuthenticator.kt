package com.farshad.topmovies_compose.data.remote.interceptors

import android.content.Context
import com.farshad.moviesAppCompose.data.remote.AuthService
import com.farshad.topmovies_compose.data.dataStore.TokenManager1
import com.farshad.topmovies_compose.data.model.network.UserAuthModel
import com.farshad.topmovies_compose.util.Constants.BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    @ApplicationContext val context: Context
) : Authenticator {

    private val tokenManager= TokenManager1(context)

    override fun authenticate(route: Route?, response: Response): Request? {


        return runBlocking(Dispatchers.IO) {

            val refreshToken=tokenManager.getRefreshToken.first()
            val refreshTokenB:RequestBody= refreshToken.toRequestBody()
            val grantTypeB:RequestBody= "refresh_token".toRequestBody()

            val newAccessToken = getUpdatedToken(refreshTokenB,grantTypeB)

            if (!newAccessToken.isSuccessful){
                tokenManager.deleteToken()
            }else{
                tokenManager.saveToken(newAccessToken.body()) // save new access_token for next call
            }


            newAccessToken.body()?.let {
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.access_token}") // just only need to override "Authorization" header, don't need to override all header since this new request is create base on old request
                    .build()
            }
        }

    }




    private suspend fun getUpdatedToken( refreshToken:RequestBody,grantType:RequestBody): retrofit2.Response<UserAuthModel> {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


        val service=retrofit.create(AuthService::class.java)
        return service.refreshTokenFromApi(refreshToken,grantType)


    }
}