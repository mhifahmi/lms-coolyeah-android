package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileData(

    val id:Long,

    val username:String,

    @SerializedName("full_name")
    val fullName:String,

    val role:String,

    val department:String,

    val position:String

)