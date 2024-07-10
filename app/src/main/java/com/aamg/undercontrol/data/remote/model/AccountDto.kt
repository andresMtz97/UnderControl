package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class AccountDto(
    @SerializedName("cuenta_id") val id: Long? = null,
    @SerializedName("nombre") var name: String,
    @SerializedName("saldo") var balance: Double,
    @SerializedName("usuario_id") val userId: Long? = null
)
