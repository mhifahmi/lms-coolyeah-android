package com.mhifahmi.lmscoolyeah.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun create(
        context: Context
    ): ApiService {

        val logging =
            HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            }

        val client =
            OkHttpClient.Builder()
                .addInterceptor(
                    AuthInterceptor(context)
                )
                .addInterceptor(logging)
                .build()

        return Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ApiService::class.java)

    }

}