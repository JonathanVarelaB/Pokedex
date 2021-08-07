package com.jvarela.pokdex.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _serverError = MutableLiveData<Boolean>()
    val serverError: LiveData<Boolean> = _serverError

    fun showError(){
        _serverError.postValue(true)
    }

    fun showLoader(){
        _isLoading.postValue(true)
    }

    fun hideLoader(){
        _isLoading.postValue(false)
    }
}