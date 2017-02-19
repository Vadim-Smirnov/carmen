package net.ginteam.carmen.kotlin.manager

import android.util.Log
import net.ginteam.carmen.BuildConfig
import net.ginteam.carmen.kotlin.api.ApiSettings
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */
object ApiManager {

    private const val CONNECTION_TIMEOUT: Long = 5

    val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
    }

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val userAccessToken = getUserAccessToken()
                    val locationHeader = getLocationHeader()

                    val originalRequest = chain.request()
                    val newRequest = originalRequest
                            .newBuilder()
                            .addHeader(ApiSettings.Auth.Params.AUTH_HEADER, userAccessToken)
                            .addHeader(ApiSettings.Catalog.Params.LATITUDE, locationHeader.first)
                            .addHeader(ApiSettings.Catalog.Params.LONGITUDE, locationHeader.second)
                            .build()

                    Log.d("ApiManager.kt", "Request URL: ${newRequest.url()}")
                    Log.d("ApiManager.kt", "Request headers: ${newRequest.headers()}")

                    chain.proceed(newRequest)
                }
                .build()
    }

    private fun getLocationHeader(): Pair <String, String> {
        val preferences: PreferencesManager = SharedPreferencesManager
        val location = preferences.userLocation
        if (location == null) {
            return Pair("0", "0")
        } else {
            return Pair(location.latitude.toString(), location.longitude.toString())
        }
    }

    private fun getUserAccessToken(): String {
        val preferences: PreferencesManager = SharedPreferencesManager
        return "Bearer ${preferences.userAccessToken}"
    }

}