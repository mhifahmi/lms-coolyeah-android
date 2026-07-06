package com.mhifahmi.lmscoolyeah.network

import com.mhifahmi.lmscoolyeah.data.remote.request.ApproveLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.LoginRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.RejectLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveListResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.BaseResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.DashboardResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeListResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveDetailResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveTypeResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.LoginResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/v1/profile")
    suspend fun profile(): ProfileResponse

    @GET("api/v1/leave-types")
    suspend fun getLeaveTypes(): LeaveTypeResponse

    @GET("api/v1/dashboard")
    suspend fun dashboard(): DashboardResponse

    @GET("api/v1/leave-requests")
    suspend fun getLeaveList(
        @Query("status")
        status: List<String>? = null
    ): LeaveListResponse

    @POST("api/v1/leave-requests")
    suspend fun createLeave()

    @GET("api/v1/leave-requests/{id}")
    suspend fun getLeaveDetail(@Path("id") id: Long): LeaveDetailResponse

    @PUT("api/v1/leave-requests/{id}/cancel")
    suspend fun cancelLeave(@Path("id") id: Long): BaseResponse

    @PUT("api/v1/leave-requests/{id}/approve")
    suspend fun approveLeave(
        @Path("id") id: Long,
        @Body request: ApproveLeaveRequest
    ): BaseResponse

    @PUT("/api/v1/leave-requests/{id}/reject")
    suspend fun rejectLeave(
        @Path("id") id: Long,
        @Body request: RejectLeaveRequest
    ): BaseResponse

    @GET("/api/v1/employees")
    suspend fun getEmployees(): Response<EmployeeListResponse>
}