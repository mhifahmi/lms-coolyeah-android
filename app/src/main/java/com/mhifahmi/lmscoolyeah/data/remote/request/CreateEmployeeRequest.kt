package com.mhifahmi.lmscoolyeah.data.remote.request

data class CreateEmployeeRequest(

    val username: String,

    val password: String,

    val full_name: String,

    val email: String,

    val phone: String,

    val birth_date: String?,

    val hire_date: String,

    val department_id: Long,

    val position_id: Long

)