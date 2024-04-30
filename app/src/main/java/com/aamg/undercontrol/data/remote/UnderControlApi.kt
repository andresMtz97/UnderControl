package com.aamg.undercontrol.data.remote

import com.aamg.undercontrol.data.remote.model.ApiResponse
import com.aamg.undercontrol.data.remote.model.SignInData
import com.aamg.undercontrol.data.remote.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UnderControlApi {

    @POST("api/usuarios")
    fun signUp(@Body user: User): Call<ApiResponse>

    @POST("api/usuarios/login")
    fun signIn(@Body data: SignInData): Call<User>
}