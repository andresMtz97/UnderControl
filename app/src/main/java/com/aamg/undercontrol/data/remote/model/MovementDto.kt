package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovementDto(
    @SerializedName("movimiento_id") val id: Long? = null,
    @SerializedName("monto") val amount: Double,
    @SerializedName("fecha") val date: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("cuenta_id") val accountId: Long,
    @SerializedName("transaccion") val transaction: TransactionDto? = null,
    @SerializedName("transferencia") val transfer: TransferDto? = null
)