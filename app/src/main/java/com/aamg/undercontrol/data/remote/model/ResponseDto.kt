package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("errors") val errors: ArrayList<ValidationError>? = null,
    @SerializedName("data") val data: T? = null
)

data class ValidationError(
    @SerializedName("field") val field: String,
    @SerializedName("messages") val messages: ArrayList<String>
)