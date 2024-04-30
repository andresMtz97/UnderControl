package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status") val status: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: ArrayList<ValidationError>?
)

data class ValidationError(
    @SerializedName("field") val field: String,
    @SerializedName("messages") val messages: ArrayList<String>
)