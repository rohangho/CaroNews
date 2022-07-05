package com.example.caronews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronews.repository.MainReository
import com.example.caronews.repository.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val mainRepository: MainReository
) : ViewModel() {

    private val _responseData: MutableLiveData<ResponseState> = MutableLiveData()
    val responseData: LiveData<ResponseState> = _responseData

    fun getNewsDetail() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _responseData.postValue(mainRepository.getData())
            }
        }
    }
}