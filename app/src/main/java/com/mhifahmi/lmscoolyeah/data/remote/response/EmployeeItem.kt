package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName

data class EmployeeItem(

    @SerializedName("id")
    val id: Long,

    @SerializedName("employee_code")
    val employeeCode: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("position")
    val position: String,

    @SerializedName("status")
    val status: String

)