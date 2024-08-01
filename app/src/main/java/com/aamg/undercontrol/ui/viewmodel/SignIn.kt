package com.aamg.undercontrol.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.ResponseDto
import com.aamg.undercontrol.data.remote.model.SignInData
import com.aamg.undercontrol.data.remote.model.UserDto
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn: ViewModel() {
    private val repository = UnderControlRepository.getInstance()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signIn(data: SignInData) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val call = repository.signIn(data)
            call.enqueue(object: Callback<UserDto> {
                override fun onResponse(p0: Call<UserDto>, response: Response<UserDto>) {
                    if (response.isSuccessful && response.body() != null) {
                        DataProvider.currentUser = response.body()
                        _success.postValue(true)
                    } else {
                        val apiResponse = Gson().fromJson(
                            response.errorBody()!!.string(),
                            ResponseDto::class.java
                        )
                        apiResponse.message?.let {
                            _error.postValue(it)
                        }
                    }
                    _isLoading.postValue(false)
                }

                override fun onFailure(p0: Call<UserDto>, t: Throwable) {
                     _error.postValue(t.message.toString())
                    _isLoading.postValue(false)
                }

            })
        }
    }
}