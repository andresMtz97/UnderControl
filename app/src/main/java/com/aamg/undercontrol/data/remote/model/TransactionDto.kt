package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class TransactionDto(
    @SerializedName("movimiento_id") val id: Long,
    @SerializedName("tipo") val type: Boolean,
    @SerializedName("categoria_id") val categoryId: Long
)
