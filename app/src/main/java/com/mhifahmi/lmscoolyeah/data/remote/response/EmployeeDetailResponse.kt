package com.mhifahmi.lmscoolyeah.data.remote.response

import com.google.gson.annotations.SerializedName


data class EmployeeDetailResponse(
    @SerializedName("data")
    val data: EmployeeDetail
)

data class EmployeeDetail(

    @SerializedName("id")
    val id: Long,

    @SerializedName("employee_code")
    val employeeCode: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("birth_date")
    val birthDate: String?,

    @SerializedName("hire_date")
    val hireDate: String,

    @SerializedName("department_id")
    val departmentId: Long,

    @SerializedName("department_name")
    val departmentName: String,

    @SerializedName("position_id")
    val positionId: Long,

    @SerializedName("position_name")
    val positionName: String,

    @SerializedName("employment_status")
    val employmentStatus: String,

    @SerializedName("is_active")
    val isActive: Boolean

)