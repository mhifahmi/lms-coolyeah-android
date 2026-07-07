package com.mhifahmi.lmscoolyeah.network

import com.mhifahmi.lmscoolyeah.data.remote.request.ApproveLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.CreateEmployeeRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.CreateLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.LoginRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.RejectLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeApiResponse
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeStatusApiResponse
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeStatusRequest
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveListResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.BaseResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.CreateEmployeeApiResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.CreateLeaveApiResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.DashboardResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.DepartmentResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeDetail
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeDetailResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeListResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveDetailResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveTypeResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.LoginResponse
import com.mhifahmi.lmscoolyeah.data.remote.response.PositionResponse
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
    suspend fun createLeave(

        @Body request: CreateLeaveRequest

    ): Response<CreateLeaveApiResponse>

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

    @GET("api/v1/departments")
    suspend fun getDepartments(): Response<DepartmentResponse>

    @GET("api/v1/positions")
    suspend fun getPositions(): Response<PositionResponse>

    @POST("api/v1/employees")
    suspend fun createEmployee(
        @Body request: CreateEmployeeRequest
    ): Response<CreateEmployeeApiResponse>

    @GET("api/v1/employees/{id}")
    suspend fun getEmployeeDetail(
        @Path("id")
        id: Long
    ): Response<EmployeeDetailResponse>

    @PUT("api/v1/employees/{id}")
    suspend fun updateEmployee(
        @Path("id")
        id: Long,
        @Body
        request: UpdateEmployeeRequest
    ): Response<UpdateEmployeeApiResponse>

    @PUT("api/v1/employees/{id}/status")
    suspend fun updateEmployeeStatus(

        @Path("id")
        id: Long,

        @Body
        request: UpdateEmployeeStatusRequest

    ): Response<UpdateEmployeeStatusApiResponse>
}