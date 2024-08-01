package com.aamg.undercontrol.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.ResponseDto
import com.aamg.undercontrol.data.remote.model.UserDto
import com.aamg.undercontrol.data.remote.model.ValidationError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signUp(user: UserDto) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val call = repository.signUp(user)

            call.enqueue(object : Callback<ResponseDto<UserDto>> {
                override fun onResponse(
                    p0: Call<ResponseDto<UserDto>>,
                    response: retrofit2.Response<ResponseDto<UserDto>>
                ) {
                    val apiResponse: ResponseDto<UserDto>?

                    // Response status 200
                    if (response.isSuccessful && response.body() != null) {
                        apiResponse = response.body()
                        apiResponse?.message.let {
                            _successMsg.postValue(it)
                        }
                    } else if (response.errorBody() != null) { // Response status 400
                        apiResponse = Gson().fromJson(
                            response.errorBody()!!.string(),
                            object: TypeToken<ResponseDto<UserDto>>() {}.type
                        )
                        apiResponse?.errors?.let { errors ->
                            if (errors.isNotEmpty()) {
                                _errors.postValue(errors)
                            }
                        }
                    } else {
                        Log.i("API_RESPONSE", "Error!")
                    }
                    _isLoading.postValue(false)
                }

                override fun onFailure(p0: Call<ResponseDto<UserDto>>, t: Throwable) {
                    _error.postValue(t.message.toString())
                }
            })
        }
    }


}