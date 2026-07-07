package com.mhifahmi.lmscoolyeah.data.remote.response

data class PositionResponse(

    val success: Boolean,

    val message: String,

    val data: List<PositionItem>

)

data class PositionItem(

    val id: Long,

    val code: String,

    val name: String

)