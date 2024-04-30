package com.aamg.undercontrol.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.ApiResponse
import com.aamg.undercontrol.data.remote.model.User
import com.aamg.undercontrol.data.remote.model.ValidationError
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SignUp : ViewModel() {
    private val repository = UnderControlRepository.getInstance()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _successMsg = MutableLiveData<String>()
    val successMsg: LiveData<String> = _successMsg

    private val _errors = MutableLiveData<ArrayList<ValidationError>>()
    val errors: LiveData<ArrayList<ValidationError>> = _errors

    fun signUp(user: User) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val call = repository.signUp(user)

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    p0: Call<ApiResponse>,
                    response: retrofit2.Response<ApiResponse>
                ) {
                    val apiResponse: ApiResponse?
                    _isLoading.postValue(false)

                    // Response status 200
                    if (response.isSuccessful && response.body() != null) {
                        apiResponse = response.body()
                        apiResponse?.message.let {
                            _successMsg.postValue(it)
                        }
                    } else if (response.errorBody() != null) { // Response status 400
                        apiResponse = Gson().fromJson(
                            response.errorBody()!!.string(),
                            ApiResponse::class.java
                        )
                        apiResponse.errors?.let { errors ->
                            if (errors.isNotEmpty()) {
                                _errors.postValue(errors)
                            }
                        }
                    } else {
                        Log.i("API_RESPONSE", "Error!")
                    }
                }

                override fun onFailure(p0: Call<ApiResponse>, t: Throwable) {
                    Log.e("SIGN_UP_FAILURE", t.message.toString())
                }
            })
        }
    }


}