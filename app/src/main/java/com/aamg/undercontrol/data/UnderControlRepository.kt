package com.aamg.undercontrol.data

import com.aamg.undercontrol.data.remote.RetrofitHelper
import com.aamg.undercontrol.data.remote.UnderControlApi
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.ResponseDto
import com.aamg.undercontrol.data.remote.model.SignInData
import com.aamg.undercontrol.data.remote.model.UserDto
import retrofit2.Call

class UnderControlRepository {
    val retrofit = RetrofitHelper.getRetrofit()
    private val api = retrofit.create(UnderControlApi::class.java)

    fun signUp(user: UserDto): Call<ResponseDto<UserDto>> = api.signUp(user)

    fun signIn(data: SignInData): Call<UserDto> = api.signIn(data)

    fun getCategories(jwt: String, type: String): Call<ArrayList<CategoryDto>> = api.getCategories(jwt, type)

    fun addCategory(jwt: String, type: String, category: CategoryDto): Call<ResponseDto<CategoryDto>> = api.addCategory(jwt, type, category)

    fun getAccounts(jwt: String): Call<ArrayList<AccountDto>> = api.getAccounts(jwt)

    fun addAccount(jwt: String, account: AccountDto): Call<ResponseDto<AccountDto>> =
        api.addAccount(jwt, account)

    companion object {
        @Volatile
        private var instance: UnderControlRepository? = null

        fun getInstance(): UnderControlRepository = instance ?: synchronized(this) {
            instance ?: UnderControlRepository().also { instance = it }
        }
    }
}