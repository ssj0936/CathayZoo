package com.timothy.zoo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timothy.zoo.data.DataSource
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val dataSource: DataSource
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(dataSource) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}