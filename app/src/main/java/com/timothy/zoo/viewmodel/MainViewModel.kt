package com.timothy.zoo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timothy.zoo.data.DataSource
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.data.model.ZooSectionResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataSource: DataSource
):ViewModel() {

    val mZooSectionResultsItem = MutableLiveData<List<ZooSectionResultsItem?>>()

    val test:MutableLiveData<String> = MutableLiveData<String>().apply {
        this.value = "VIEW MODEL TEST"
    }

    fun queryZooSectionData(): Observable<List<ZooSectionResultsItem?>> {
        return dataSource.queryZooSectionData()
    }
}