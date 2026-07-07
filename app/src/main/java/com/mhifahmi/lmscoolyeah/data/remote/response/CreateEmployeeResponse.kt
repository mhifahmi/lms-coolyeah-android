package com.mhifahmi.lmscoolyeah.data.remote.response

data class CreateEmployeeResponse(

    val id: Long,

    val employee_code: String

)

data class CreateEmployeeApiResponse(

    val success: Boolean,

    val message: String,

    val data: CreateEmployeeResponse

)