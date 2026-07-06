package com.mhifahmi.lmscoolyeah.data.repository

import android.content.Context
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeItem
import com.mhifahmi.lmscoolyeah.network.ApiConfig
import com.mhifahmi.lmscoolyeah.network.ServiceLocator

class EmployeeRepository(
    context: Context
) {

    private val api =
        ServiceLocator.getApiService(context)

    suspend fun getEmployees():
            Result<List<EmployeeItem>> {

        return try {

            val response =
                api.getEmployees()

            if (
                response.isSuccessful &&
                response.body() != null
            ) {

                Result.success(
                    response.body()!!.data
                )

            } else {

                Result.failure(

                    Exception(
                        response.message()
                    )

                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}