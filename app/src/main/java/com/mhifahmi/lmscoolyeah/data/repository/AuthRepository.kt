package com.mhifahmi.lmscoolyeah.data.repository

import android.content.Context
import com.mhifahmi.lmscoolyeah.data.remote.request.LoginRequest
import com.mhifahmi.lmscoolyeah.data.remote.response.LoginData
import com.mhifahmi.lmscoolyeah.data.remote.response.ProfileData
import com.mhifahmi.lmscoolyeah.network.ServiceLocator

class AuthRepository(context: Context){

    private val api = ServiceLocator.getApiService(context)

    suspend fun login(
        username: String,
        password: String
    ): Result<LoginData> {
        return try {
            val response = api.login(LoginRequest(
                    username,
                    password
                )
            )

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

    suspend fun profile(): Result<ProfileData> {
        return try {
            val response = api.profile()
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Terjadi kesalahan koneksi")
            )
        }
    }
}