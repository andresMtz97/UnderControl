package com.aamg.undercontrol.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.MovementDto
import com.aamg.undercontrol.utils.capitalize
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Home : ViewModel() {
    private val repository = UnderControlRepository.getInstance()
    private val token = DataProvider.currentUser?.token

    private val _movements = MutableLiveData<ArrayList<MovementDto>>()
    val movements: LiveData<ArrayList<MovementDto>> = _movements

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _month = MutableLiveData<String>()
    val month: LiveData<String> = _month

    init {
        viewModelScope.launch {
            if (token != null) {
                // Get accounts
                getAccounts()
                // Get income categories
                getIncomeCategories()
                // Get expense categories
                getExpenseCategories()
            }
        }
    }

    fun onCreate() {
        _loading.postValue(true)

        val monthFormatter = DateTimeFormatter.ofPattern("MMMM")
        val month = monthFormatter.format(LocalDateTime.now()).capitalize()
        _month.postValue(month)

        if (DataProvider.currentUser != null) {
            DataProvider.currentUser?.let { currentUser ->
                _name.postValue(currentUser.name ?: "")
            }
        }
        viewModelScope.launch {
            if (token != null) {
                val call: Call<ArrayList<MovementDto>> = repository.getMovements("Bearer $token")
                call.enqueue(object: Callback<ArrayList<MovementDto>> {
                    override fun onResponse(
                        p0: Call<ArrayList<MovementDto>>,
                        r: Response<ArrayList<MovementDto>>
                    ) {
                        Log.d("Homeviewmodel-movements", r.body().toString())
                        _movements.postValue(r.body())
                        _loading.postValue(false)
                    }

                    override fun onFailure(p0: Call<ArrayList<MovementDto>>, t: Throwable) {
                        Log.e("Movements_error", t.message.toString())
                    }

                })
            }
        }
    }

    fun create(movement: MovementDto) {
        Log.d("Homeviewmodel-create", movement.toString())
    }

    fun update(movement: MovementDto, position: Int) {

    }

    private fun getAccounts() {
        val call: Call<ArrayList<AccountDto>> = repository.getAccounts("Bearer $token")
        call.enqueue(object: Callback<ArrayList<AccountDto>> {
            override fun onResponse(
                p0: Call<ArrayList<AccountDto>>,
                r: Response<ArrayList<AccountDto>>
            ) {
                Log.i("Homeviewmodel-accounts", r.body().toString())
                if (r.isSuccessful && r.body() != null) {
                    val data = r.body()!!
                    DataProvider.accounts = data
                }
            }

            override fun onFailure(p0: Call<ArrayList<AccountDto>>, t: Throwable) {
                Log.e("ERROR_CALL", t.message.toString())
            }

        })
    }

    private fun getIncomeCategories() {
        val callIncome: Call<ArrayList<CategoryDto>> =
            repository.getCategories("Bearer $token", "ingreso")
        callIncome.enqueue(object : Callback<ArrayList<CategoryDto>> {
            override fun onResponse(
                p0: Call<ArrayList<CategoryDto>>,
                r: Response<ArrayList<CategoryDto>>
            ) {
                Log.i("Homeviewmodel-income", r.body().toString())
                if (r.isSuccessful && r.body() != null) {
                    val data = r.body()!!
                    DataProvider.incomeCategories = data
                }
               // _incomeCategories.postValue(r.body())
            }

            override fun onFailure(p0: Call<ArrayList<CategoryDto>>, t: Throwable) {
                Log.e("ERROR_CALL", t.message.toString())
            }

        })
    }

    private fun getExpenseCategories() {
        val callExpense: Call<ArrayList<CategoryDto>> =
            repository.getCategories("Bearer $token", "egreso")
        callExpense.enqueue(object : Callback<ArrayList<CategoryDto>> {
            override fun onResponse(
                p0: Call<ArrayList<CategoryDto>>,
                r: Response<ArrayList<CategoryDto>>
            ) {
                Log.i("Homeviewmodel-expense", r.body().toString())
                if (r.isSuccessful && r.body() != null) {
                    val data = r.body()!!
                    DataProvider.expenseCategories = data
                }
            }

            override fun onFailure(p0: Call<ArrayList<CategoryDto>>, t: Throwable) {
                Log.e("ERROR_CALL", t.message.toString())
            }
        })
    }

}