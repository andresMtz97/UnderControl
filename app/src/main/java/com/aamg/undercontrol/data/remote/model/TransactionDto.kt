package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class TransactionDto(
    @SerializedName("movimiento_id") val id: Long? = null,
    @SerializedName("tipo") var type: Boolean,
    @SerializedName("categoria_id") var categoryId: Long,
    @SerializedName("categoria") val category: CategoryDto? = null,
)
