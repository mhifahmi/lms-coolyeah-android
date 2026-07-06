package com.mhifahmi.lmscoolyeah.data.remote.response

data class LeaveListResponse(
    val success: Boolean,
    val data: List<RecentApproval>
)