package com.mhifahmi.lmscoolyeah.data.repository

import android.content.Context
import com.mhifahmi.lmscoolyeah.data.remote.request.ApproveLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.RejectLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveDetail
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveType
import com.mhifahmi.lmscoolyeah.data.remote.response.RecentApproval
import com.mhifahmi.lmscoolyeah.network.RetrofitClient

class LeaveRepository(context: Context){
    private val api = RetrofitClient.create(context)

    suspend fun getLeaveTypes(): Result<List<LeaveType>> {
        return try {
            val response = api.getLeaveTypes()
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(
                    Exception(response.message)
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLeaveDetail(
        id: Long
    ): Result<LeaveDetail> {

        return try {

            val response =
                api.getLeaveDetail(id)

            if(response.success){

                Result.success(response.data)

            }else{

                Result.failure(
                    Exception("Data tidak ditemukan")
                )

            }

        }catch(e:Exception){

            Result.failure(e)

        }

    }

    suspend fun approveLeave(
        id: Long,
        notes: String?
    ): Result<String> {

        return try {

            val response =
                api.approveLeave(
                    id,
                    ApproveLeaveRequest(notes)
                )

            if (response.success) {

                Result.success(response.message)

            } else {

                Result.failure(
                    Exception(response.message)
                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun rejectLeave(

        id: Long,

        reason: String

    ): Result<String> {

        return try {

            val response =
                api.rejectLeave(
                    id,
                    RejectLeaveRequest(reason)
                )

            if (response.success) {

                Result.success(response.message)

            } else {

                Result.failure(
                    Exception(response.message)
                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun cancelLeave(
        id: Long
    ): Result<String> {

        return try {

            val response =
                api.cancelLeave(id)

            if (response.success) {

                Result.success(
                    response.message
                )

            } else {

                Result.failure(
                    Exception(response.message)
                )

            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun getLeaveList(status: List<String>?):

            Result<List<RecentApproval>> {

        return try {

            val response =
                api.getLeaveList(status)

            Result.success(
                response.data
            )

        } catch (e: Exception) {

            Result.failure(e)

        }

    }
}