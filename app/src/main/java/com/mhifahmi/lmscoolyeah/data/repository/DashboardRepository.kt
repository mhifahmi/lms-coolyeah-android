package com.mhifahmi.lmscoolyeah.data.repository

import android.content.Context
import com.mhifahmi.lmscoolyeah.data.remote.response.DashboardData
import com.mhifahmi.lmscoolyeah.network.ServiceLocator

class DashboardRepository(context: Context) {

    private val api = ServiceLocator.getApiService(context)

    suspend fun getDashboard(): Result<DashboardData> {
        return try {
            val response = api.dashboard()

            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(
                    Exception(response.message)
                )
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Terjadi kesalahan koneksi")
            )
        }
    }
}