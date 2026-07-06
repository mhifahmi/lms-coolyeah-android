package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginData(

    @SerializedName("token")
    val token:String,

    @SerializedName("user_id")
    val userId:Long,

    @SerializedName("username")
    val username:String,

    @SerializedName("full_name")
    val fullName:String,

    @SerializedName("role")
    val role:String

)