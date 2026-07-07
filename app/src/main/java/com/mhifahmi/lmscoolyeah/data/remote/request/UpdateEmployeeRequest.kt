package com.mhifahmi.lmscoolyeah.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateEmployeeRequest(

    val username: String,

    val full_name: String,

    val email: String,

    val phone: String,

    val birth_date: String?,

    val hire_date: String,

    val department_id: Long,

    val position_id: Long

)

data class UpdateEmployeeApiResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: UpdateEmployeeResponse

)

data class UpdateEmployeeResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("employee_code")
    val employeeCode: String

)

data class UpdateEmployeeStatusRequest(

    val status: String

)

data class UpdateEmployeeStatusResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("employment_status")
    val employmentStatus: String

)

data class UpdateEmployeeStatusApiResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: UpdateEmployeeStatusResponse

)