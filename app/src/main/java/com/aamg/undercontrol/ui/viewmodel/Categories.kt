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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _incomeCategories = MutableLiveData<ArrayList<CategoryDto>>()
    val incomeCategories: LiveData<ArrayList<CategoryDto>> = _incomeCategories

    private val _expenseCategories = MutableLiveData<ArrayList<CategoryDto>>()
    val expenseCategories: LiveData<ArrayList<CategoryDto>> = _expenseCategories

    private val _errors = MutableLiveData<ArrayList<ValidationError>>()
    val errors: LiveData<ArrayList<ValidationError>> = _errors

    fun onCreate() {
        _loading.postValue(true)
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
                        _loading.postValue(false)
                    }

                    override fun onFailure(p0: Call<ArrayList<CategoryDto>>, t: Throwable) {
                        Log.e("ERROR_CALL", t.message.toString())
                    }
                })
            }
        }
    }

    fun createCategory(category: CategoryDto) {
        _loading.postValue(true)
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
                    _loading.postValue(false)
                }

                override fun onFailure(p0: Call<ResponseDto<CategoryDto>>, t: Throwable) {
                    Log.e("ERROR_CALL", t.message.toString())
                }

            })
        }
    }

    fun updateCategory(category: CategoryDto, position: Int) {
        _loading.postValue(true)
        Log.i("Accounts", category.toString())
        viewModelScope.launch {
            val call: Call<ResponseDto<CategoryDto>> =
                repository.updateCategory("Bearer $token", category.id!!, category)
            call.enqueue(object : Callback<ResponseDto<CategoryDto>> {
                override fun onResponse(
                    p0: Call<ResponseDto<CategoryDto>>,
                    r: Response<ResponseDto<CategoryDto>>
                ) {
                    Log.i("Categories", r.body().toString())
                    if (r.isSuccessful && r.body() != null) {
                        val newList =
                            ArrayList(if (category.income) _incomeCategories.value!! else _expenseCategories.value!!)
                        newList[position] = r.body()!!.data

                        if (category.income) {
                            _incomeCategories.postValue(newList)
                        } else {
                            _expenseCategories.postValue(newList)
                        }
                    }
                    _loading.postValue(false)
                }

                override fun onFailure(p0: Call<ResponseDto<CategoryDto>>, t: Throwable) {
                    Log.e("ERROR_CALL", t.message.toString())
                }
            })
        }
    }

    fun deleteCategory(category: CategoryDto, position: Int) {
        _loading.postValue(true)
        viewModelScope.launch {
            val call: Call<ResponseDto<CategoryDto>> =
                repository.deleteCategory("Bearer $token", category.id!!)
            call.enqueue(object : Callback<ResponseDto<CategoryDto>> {
                override fun onResponse(
                    p0: Call<ResponseDto<CategoryDto>>,
                    r: Response<ResponseDto<CategoryDto>>
                ) {
                    Log.i("Categories", r.body().toString())
                    if (r.isSuccessful && r.body() != null) {
                        val newList =
                            ArrayList(if (category.income) _incomeCategories.value!! else _expenseCategories.value!!)
                        newList.removeAt(position)
                        if (category.income) {
                            _incomeCategories.postValue(newList)
                        } else {
                            _expenseCategories.postValue(newList)
                        }
                    }
                    _loading.postValue(false)
                }

                override fun onFailure(p0: Call<ResponseDto<CategoryDto>>, t: Throwable) {
                    Log.e("ERROR_CALL", t.message.toString())
                }
            })
        }
    }
}