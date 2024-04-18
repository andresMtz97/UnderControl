package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categoria_id") val id: Long,
    @SerializedName("nombre") var name: String,
    @SerializedName("tipo") var income: Boolean,
    @SerializedName("usuario_id") val userId: Long
)
