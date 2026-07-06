package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    val success: Boolean,
    val message: String,
    val data: DashboardData
)

data class DashboardData(
    val role: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("employee_code")
    val employeeCode: String? = null,

    val department: String? = null,

    val position: String? = null,

    @SerializedName("leave_balance")
    val leaveBalance: LeaveBalance? = null,

    val summary: AdminDashboardSummary? = null,

    @SerializedName("recent_leave")
    val recentLeave: List<RecentLeave> = emptyList(),

    @SerializedName("recent_approval")
    val recentApproval: List<RecentApproval> = emptyList()
)

data class AdminDashboardSummary(
    @SerializedName("total_employee")
    val totalEmployee: Int,

    @SerializedName("pending_request")
    val pendingRequest: Int,

    @SerializedName("approved_request")
    val approvedRequest: Int,

    @SerializedName("rejected_request")
    val rejectedRequest: Int
)

data class RecentLeave(
    val id: Long,

    @SerializedName("request_number")
    val requestNumber: String,

    @SerializedName("leave_type")
    val leaveType: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    @SerializedName("total_days")
    val totalDays: Int,

    val status: String
)

data class RecentApproval(

    val id: Long,

    @SerializedName("request_number")
    val requestNumber: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("leave_type")
    val leaveType: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    val status: String
)

data class LeaveBalance(
    @SerializedName("annual_remaining")
    val annualRemaining: Int,

    @SerializedName("carry_over_remaining")
    val carryOverRemaining: Int,
)