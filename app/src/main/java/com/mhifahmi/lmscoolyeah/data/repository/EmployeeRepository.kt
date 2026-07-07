package com.mhifahmi.lmscoolyeah.data.repository

import android.content.Context
import com.mhifahmi.lmscoolyeah.data.remote.request.CreateEmployeeRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeResponse
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeStatusRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeStatusResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.CreateEmployeeResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.DepartmentItem
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeDetail
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeItem
import com.mhifahmi.lmscoolyeah.data.remote.response.PositionItem
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

    suspend fun getDepartments():
            Result<List<DepartmentItem>> {

        return try {

            val response =
                api.getDepartments()

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

    suspend fun getPositions():
            Result<List<PositionItem>> {

        return try {

            val response =
                api.getPositions()

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

    suspend fun updateEmployee(
        id: Long,
        request: UpdateEmployeeRequest
    ): Result<UpdateEmployeeResponse> {

        return try {

            val response =
                api.updateEmployee(id, request)

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
                        response.body()?.message
                            ?: response.message()
                    )

                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun getEmployeeDetail(
        id: Long
    ): Result<EmployeeDetail> {

        return try {

            val response =
                api.getEmployeeDetail(id)

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

    suspend fun createEmployee(
        request: CreateEmployeeRequest
    ): Result<CreateEmployeeResponse> {

        return try {

            val response =
                api.createEmployee(request)

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
                        response.body()?.message
                            ?: response.message()
                    )

                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }


    suspend fun updateEmployeeStatus(

        id: Long,

        status: String

    ): Result<UpdateEmployeeStatusResponse> {

        return try {

            val response =

                api.updateEmployeeStatus(

                    id,

                    UpdateEmployeeStatusRequest(
                        status
                    )

                )

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

                        response.errorBody()
                            ?.string()
                            ?: response.message()

                    )

                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }
}