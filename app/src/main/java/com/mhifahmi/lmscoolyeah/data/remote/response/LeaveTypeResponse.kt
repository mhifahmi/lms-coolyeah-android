package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class LeaveTypeResponse(
    val success: Boolean,
    val message: String,
    val data: List<LeaveType>
)

data class LeaveType(
    val id: Long,
    val code: String,
    val name: String,

    @SerializedName("max_days")
    val maxDays: Int,

    @SerializedName("require_attachment")
    val requireAttachment: Boolean,

    @SerializedName("is_paid")
    val isPaid: Boolean,

    val description: String
)