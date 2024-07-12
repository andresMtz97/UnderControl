package com.aamg.undercontrol.data.remote

import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.ResponseDto
import com.aamg.undercontrol.data.remote.model.SignInData
import com.aamg.undercontrol.data.remote.model.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UnderControlApi {

    @POST("api/usuarios")
    fun signUp(@Body user: UserDto): Call<ResponseDto<UserDto>>

    @POST("api/usuarios/login")
    fun signIn(@Body data: SignInData): Call<UserDto>

    @GET("api/categorias/{type}")
    fun getCategories(
        @Header("Authorization") jwt: String,
        @Path("type") type: String
    ): Call<ArrayList<CategoryDto>>

    @POST("api/categorias/{type}")
    fun addCategory(
        @Header("Authorization") jwt: String,
        @Path("type") type: String,
        @Body category: CategoryDto
    ): Call<ResponseDto<CategoryDto>>

    @PUT("api/categorias/{id}")
    fun updateCategory(
        @Header("Authorization") jwt: String,
        @Path("id") id: String,
        @Body category: CategoryDto
    ): Call<ResponseDto<CategoryDto>>

    @DELETE("api/categorias/{id}")
    fun deleteCategory(
        @Header("Authorization") jwt: String,
        @Path("id") id: String
    ): Call<ResponseDto<CategoryDto>>

    @GET("/api/cuentas")
    fun getAccounts(@Header("Authorization") jwt: String): Call<ArrayList<AccountDto>>

    @POST("/api/cuentas")
    fun addAccount(
        @Header("Authorization") jwt: String,
        @Body account: AccountDto
    ): Call<ResponseDto<AccountDto>>

    @PUT("/api/cuentas/{id}")
    fun updateAccount(
        @Header("Authorization") jwt: String,
        @Path("id") id: String,
        @Body account: AccountDto
    ): Call<ResponseDto<AccountDto>>

    @DELETE("/api/cuentas/{id}")
    fun deleteAccount(
        @Header("Authorization") jwt: String,
        @Path("id") id: String
    ): Call<ResponseDto<AccountDto>>

}