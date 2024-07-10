package com.aamg.undercontrol.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.ResponseDto
import com.aamg.undercontrol.data.remote.model.ValidationError
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Categories : ViewModel() {
    private val repository = UnderControlRepository.getInstance()
    private val token = DataProvider.currentUser?.token

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _incomeCategories = MutableLiveData<ArrayList<CategoryDto>>()
    val incomeCategories: LiveData<ArrayList<CategoryDto>> = _incomeCategories

    private val _expenseCategories = MutableLiveData<ArrayList<CategoryDto>>()
    val expenseCategories: LiveData<ArrayList<CategoryDto>> = _expenseCategories

    private val _errors = MutableLiveData<ArrayList<ValidationError>>()
    val errors: LiveData<ArrayList<ValidationError>> = _errors

    fun onCreate() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            if (token != null) {
                val callIncome: Call<ArrayList<CategoryDto>> =
                    repository.getCategories("Bearer $token", "ingreso")
                callIncome.enqueue(object : Callback<ArrayList<CategoryDto>> {
                    override fun onResponse(
                        p0: Call<ArrayList<CategoryDto>>,
                        r: Response<ArrayList<CategoryDto>>
                    ) {
                        Log.i("CAT_RESPONSE", r.body().toString())
                        _incomeCategories.postValue(r.body())
                        _isLoading.postValue(false)
                    }

                    override fun onFailure(p0: Call<ArrayList<CategoryDto>>, t: Throwable) {
                        Log.e("ERROR_CALL", t.message.toString())
                    }

                })

                val callExpense: Call<ArrayList<CategoryDto>> =
                    repository.getCategories("Bearer $token", "egreso")
                callExpense.enqueue(object : Callback<ArrayList<CategoryDto>> {
                    override fun onResponse(
                        p0: Call<ArrayList<CategoryDto>>,
                        r: Response<ArrayList<CategoryDto>>
                    ) {
                        Log.i("CAT_RESPONSE", r.body().toString())
                        _expenseCategories.postValue(r.body())
                    }

                    override fun onFailure(p0: Call<ArrayList<CategoryDto>>, t: Throwable) {
                        Log.e("ERROR_CALL", t.message.toString())
                    }
                })
            }
        }
    }

    fun createCategory(category: CategoryDto) {
        _isLoading.postValue(true)
        val type = if (category.income) "ingreso" else "egreso"
        viewModelScope.launch {
            val call: Call<ResponseDto<CategoryDto>> =
                repository.addCategory("Bearer $token", type, category)
            call.enqueue(object : Callback<ResponseDto<CategoryDto>> {
                override fun onResponse(
                    p0: Call<ResponseDto<CategoryDto>>,
                    r: Response<ResponseDto<CategoryDto>>
                ) {
                    if (r.isSuccessful && r.body() != null) {
                        Log.i("CREATE_CAT", r.body().toString())
                        if (category.income) {
                            val newList = ArrayList(_incomeCategories.value!!)
                            newList.add(r.body()!!.data)
                            _incomeCategories.postValue(newList)
                        } else {
                            val newList = ArrayList(_expenseCategories.value!!)
                            newList.add(r.body()!!.data)
                            _expenseCategories.postValue(newList)
                        }
                    } else {
                        val apiResponse = Gson().fromJson(
                            r.errorBody()!!.string(),
                            ResponseDto::class.java
                        )
                        apiResponse.errors?.let {
                            _errors.postValue(it)
                        }
                    }
                    _isLoading.postValue(false)
                }

                override fun onFailure(p0: Call<ResponseDto<CategoryDto>>, t: Throwable) {
                    Log.e("ERROR_CALL", t.message.toString())
                }

            })
        }
    }

}