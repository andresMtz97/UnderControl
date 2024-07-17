package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class TransferDto(
    @SerializedName("movimiento_id") val id: Long? = null,
    @SerializedName("cuenta_id") var accountId: Long,
)