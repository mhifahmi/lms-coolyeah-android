package com.mhifahmi.lmscoolyeah.data.remote.response

data class DepartmentResponse(

    val success: Boolean,

    val message: String,

    val data: List<DepartmentItem>

)

data class DepartmentItem(

    val id: Long,

    val code: String,

    val name: String

)