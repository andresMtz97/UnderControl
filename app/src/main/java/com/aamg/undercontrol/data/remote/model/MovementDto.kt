package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovementDto(
    @SerializedName("movimiento_id") val id: Long? = null,
    @SerializedName("monto") var amount: Double,
    @SerializedName("fecha") var date: String,
    @SerializedName("descripcion") var description: String,
    @SerializedName("cuenta_id") var accountId: Long,
    @SerializedName("transaccion") var transaction: TransactionDto? = null,
    @SerializedName("transferencia") var transfer: TransferDto? = null
)