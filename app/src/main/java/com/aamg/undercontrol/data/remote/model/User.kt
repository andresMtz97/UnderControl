package com.aamg.undercontrol.data.remote.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("usuario_id") val id: Long,
    @SerializedName("nombre") var name: String,
    @SerializedName("ap_paterno") var lastName: String,
    @SerializedName("ap_materno") var motherLastName: String,
    @SerializedName("username") var username: String
)
