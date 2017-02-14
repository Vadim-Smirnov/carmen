package net.ginteam.carmen.kotlin.manager

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

    private const val CONNECTION_TIMEOUT: Long = 15

    val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientInit())
                .build()
    }

    private fun httpClientInit(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    var apiRequest = chain.request()

                    apiRequest = apiRequest
                            .newBuilder()
                            .addHeader(ApiSettings.Auth.Params.AUTH_HEADER, "sdsa")
                            .build()

                    println("Request URRRL: ${apiRequest.url()}")
                    chain.proceed(apiRequest)
                }
                .build()
    }

}