package com.timothy.zoo.viewmodel

import androidx.lifecycle.ViewModel
import com.timothy.zoo.data.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ZooSectionListViewModel public @Inject constructor(
    dataSource: DataSource
):ViewModel() {
    val mZooSectionResultsItem = dataSource.queryZooSectionData()
}