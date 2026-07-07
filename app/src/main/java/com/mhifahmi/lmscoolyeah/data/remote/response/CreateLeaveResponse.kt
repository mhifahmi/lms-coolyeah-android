package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateLeaveResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("request_number")
    val requestNumber: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String
)

typealias CreateLeaveApiResponse =
        BaseResponseAPI<CreateLeaveResponse>