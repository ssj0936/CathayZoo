package com.timothy.zoo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timothy.zoo.data.DataSource
import com.timothy.zoo.data.model.ZooSectionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataSource: DataSource
):ViewModel() {
    val test:MutableLiveData<String> = MutableLiveData<String>().apply {
        this.value = "VIEW MODEL TEST"
    }

    fun queryZooSectionData():Single<ZooSectionResponse>{
        return dataSource.queryZooSectionData()
    }
}