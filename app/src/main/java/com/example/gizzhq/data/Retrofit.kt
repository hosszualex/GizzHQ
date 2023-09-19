package com.example.gizzhq.data

import com.example.gizzhq.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {
    fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(createHttpClient())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun createHttpClient(): OkHttpClient {
        lateinit var okHttpClient: OkHttpClient
        val builder = if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(logging)
        } else {
            OkHttpClient.Builder()
        }.readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        okHttpClient = builder.build()

        return okHttpClient
    }
}