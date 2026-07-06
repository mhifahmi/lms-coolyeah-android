package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class EmployeeListResponse(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<EmployeeItem>

)