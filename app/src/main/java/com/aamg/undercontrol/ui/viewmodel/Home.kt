package com.aamg.undercontrol.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aamg.undercontrol.data.DataProvider

class Home : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    fun onCreate() {
        if (DataProvider.currentUser != null) {
            DataProvider.currentUser?.let { currentUser ->
                _name.postValue(currentUser.name ?: "")
            }

        }
    }


}