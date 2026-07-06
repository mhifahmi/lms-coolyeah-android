package com.mhifahmi.lmscoolyeah.network

import android.content.Context

object ServiceLocator {

    private var apiService: ApiService? = null

    fun getApiService(context: Context): ApiService {
        if (apiService == null) {
            apiService = RetrofitClient.create(context.applicationContext)
        }
        return apiService!!
    }
}