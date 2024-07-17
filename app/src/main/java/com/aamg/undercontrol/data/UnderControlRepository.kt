package com.aamg.undercontrol.data

import com.aamg.undercontrol.data.remote.RetrofitHelper
import com.aamg.undercontrol.data.remote.UnderControlApi
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.MovementDto
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

    fun addCategory(jwt: String, type: String, category: CategoryDto): Call<ResponseDto<CategoryDto>> =
        api.addCategory(jwt, type, category)

    fun updateCategory(
        jwt: String,
        id: Long,
        category: CategoryDto
    ): Call<ResponseDto<CategoryDto>> = api.updateCategory(jwt, id.toString(), category)

    fun deleteCategory(jwt: String, id: Long): Call<ResponseDto<CategoryDto>> =
        api.deleteCategory(jwt, id.toString())

    fun getAccounts(jwt: String): Call<ArrayList<AccountDto>> = api.getAccounts(jwt)

    fun addAccount(jwt: String, account: AccountDto): Call<ResponseDto<AccountDto>> =
        api.addAccount(jwt, account)

    fun updateAccount(jwt: String, id: Long, account: AccountDto): Call<ResponseDto<AccountDto>> =
        api.updateAccount(jwt, id.toString(), account)

    fun deleteAccount(jwt: String, id: Long): Call<ResponseDto<AccountDto>> =
        api.deleteAccount(jwt, id.toString())

    fun getMovements(jwt: String): Call<ArrayList<MovementDto>> = api.getMovements(jwt)

    fun addTransaction(jwt: String, type: String, movement: MovementDto): Call<ResponseDto<MovementDto>> =
        api.addTransaction(jwt, type, movement)

    fun addTransfer(jwt: String, movement: MovementDto): Call<ResponseDto<MovementDto>> =
        api.addTransfer(jwt, movement)

    companion object {
        @Volatile
        private var instance: UnderControlRepository? = null

        fun getInstance(): UnderControlRepository = instance ?: synchronized(this) {
            instance ?: UnderControlRepository().also { instance = it }
        }
    }
}