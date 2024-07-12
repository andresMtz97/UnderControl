package com.aamg.undercontrol.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.UnderControlRepository
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.MovementDto
import com.aamg.undercontrol.data.remote.model.ResponseDto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : ViewModel() {
    private val repository = UnderControlRepository.getInstance()
    private val token = DataProvider.currentUser?.token

    private val _movements = MutableLiveData<ArrayList<MovementDto>>()
    val movements: LiveData<ArrayList<MovementDto>> = _movements

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun onCreate() {
        _loading.postValue(true)
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
                        Log.d("Movements", r.body().toString())
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


}