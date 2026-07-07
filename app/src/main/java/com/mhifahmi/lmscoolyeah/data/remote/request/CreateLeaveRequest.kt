package com.mhifahmi.lmscoolyeah.data.remote.request

import com.google.gson.annotations.SerializedName

data class CreateLeaveRequest(

    @SerializedName("leave_type_id")
    val leaveTypeId: Long,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    @SerializedName("reason")
    val reason: String
)