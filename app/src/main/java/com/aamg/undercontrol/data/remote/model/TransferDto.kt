package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class TransferDto(
    @SerializedName("movimiento_id") val id: Long,
    @SerializedName("cuenta_id") val accountId: Long
)