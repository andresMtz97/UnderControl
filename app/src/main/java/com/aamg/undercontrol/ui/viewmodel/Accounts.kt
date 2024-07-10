package com.aamg.undercontrol.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.ResponseDto
import com.aamg.undercontrol.data.remote.model.ValidationError
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Accounts: ViewModel() {

    private val repository = UnderControlRepository.getInstance()
    private val token = DataProvider.currentUser?.token

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _accounts = MutableLiveData<ArrayList<AccountDto>>()
    val accounts: LiveData<ArrayList<AccountDto>> = _accounts

    private val _errors = MutableLiveData<ArrayList<ValidationError>>()
    val errors: LiveData<ArrayList<ValidationError>> = _errors

    fun onCreate() {
        _loading.postValue(true)
        viewModelScope.launch {
            if (token != null) {
                val call: Call<ArrayList<AccountDto>> = repository.getAccounts("Bearer $token")
                call.enqueue(object: Callback<ArrayList<AccountDto>> {
                    override fun onResponse(
                        p0: Call<ArrayList<AccountDto>>,
                        r: Response<ArrayList<AccountDto>>
                    ) {
                        _accounts.postValue(r.body())
                        _loading.postValue(false)
                    }

                    override fun onFailure(p0: Call<ArrayList<AccountDto>>, t: Throwable) {
                        Log.e("ERROR_CALL", t.message.toString())
                    }

                })
            }
        }
    }

    fun createAccount(account: AccountDto) {
        _loading.postValue(true)
        viewModelScope.launch {
            val call: Call<ResponseDto<AccountDto>> = repository.addAccount("Bearer $token", account)
            call.enqueue(object: Callback<ResponseDto<AccountDto>> {
                override fun onResponse(
                    p0: Call<ResponseDto<AccountDto>>,
                    r: Response<ResponseDto<AccountDto>>
                ) {
                    Log.i("Accounts", r.body().toString())
                    if (r.isSuccessful && r.body() != null) {
                        val newList = ArrayList(_accounts.value!!)
                        newList.add(r.body()!!.data)
                        _accounts.postValue(newList)
                    } else {
                        val apiResponse = Gson().fromJson(r.errorBody()!!.string(), ResponseDto::class.java)
                        apiResponse.errors?.let {
                            _errors.postValue(it)
                        }
                    }
                    _loading.postValue(false)
                }

                override fun onFailure(p0: Call<ResponseDto<AccountDto>>, t: Throwable) {
                    Log.e("ERROR_CALL", t.message.toString())
                }
            })
        }

    }
}