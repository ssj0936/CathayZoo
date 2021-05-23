package com.timothy.zoo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    val test:MutableLiveData<String> = MutableLiveData<String>().apply {
        this.value = "VIEW MODEL TEST"
    }

}