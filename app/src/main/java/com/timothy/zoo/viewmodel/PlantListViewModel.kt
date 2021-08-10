package com.timothy.zoo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.timothy.zoo.MainApp.Companion.appContext
import com.timothy.zoo.R
import com.timothy.zoo.data.DataSource
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.data.model.ZooSectionResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val dataSource: DataSource,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val zooSection:MutableLiveData<ZooSectionResultsItem> = MutableLiveData(
            savedStateHandle.get<ZooSectionResultsItem>("zoo_section")!!
    )
    val zooSectionName = zooSection.value?.eName ?: ""
    val zooSectionDesc = zooSection.value?.eInfo
    val zooSectionPicUrl = zooSection.value?.ePicURL
    val zooSectionCategory = zooSection.value?.eCategory
    val zooSectionMemo = zooSection.value?.eMemo
    val zooSectionLink = "<a href=\"${zooSection.value?.eURL}\">${appContext.getString(R.string.link_text_view_via_browser)}</a>"

    var mPlantResultsItem:LiveData<List<PlantResultsItem?>> = dataSource.queryPlantBySection(zooSectionName)

    val isTopTitleShow = MutableLiveData<Boolean>().apply {
        value = false
    }
}