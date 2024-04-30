package com.aamg.undercontrol.data

import com.aamg.undercontrol.data.remote.RetrofitHelper
import com.aamg.undercontrol.data.remote.UnderControlApi
import com.aamg.undercontrol.data.remote.model.ApiResponse
import com.aamg.undercontrol.data.remote.model.User
import retrofit2.Call

class UnderControlRepository {
    val retrofit = RetrofitHelper.getRetrofit()
    private val api = retrofit.create(UnderControlApi::class.java)

    fun signUp(user: User): Call<ApiResponse> = api.signUp(user)

    companion object {
        @Volatile
        private var instance: UnderControlRepository? = null

        fun getInstance(): UnderControlRepository = instance ?: synchronized(this) {
            instance ?: UnderControlRepository().also { instance = it }
        }
    }
}