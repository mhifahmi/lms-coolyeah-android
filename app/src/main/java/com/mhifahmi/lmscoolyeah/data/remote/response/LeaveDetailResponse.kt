package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class LeaveDetailResponse(
    val success: Boolean,
    val data: LeaveDetail
)

data class LeaveDetail(
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

    val reason: String,

    val status: String,

    @SerializedName("deduction_type")
    val deductionType: String,

    @SerializedName("employee_name")
    val employeeName: String? = null
)