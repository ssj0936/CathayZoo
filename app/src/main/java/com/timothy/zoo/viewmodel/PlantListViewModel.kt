package com.timothy.zoo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.timothy.zoo.MainApp.Companion.appContext
import com.timothy.zoo.R
import com.timothy.zoo.data.DataSource
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.data.model.ZooSectionResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val dataSource: DataSource,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    lateinit var disposable:Disposable
    private val zooSection:MutableLiveData<ZooSectionResultsItem> = MutableLiveData(
            savedStateHandle.get<ZooSectionResultsItem>("zoo_section")!!
    )
    val zooSectionName = zooSection.value?.eName
    val zooSectionDesc = zooSection.value?.eInfo
    val zooSectionPicUrl = zooSection.value?.ePicURL
    val zooSectionCategory = zooSection.value?.eCategory
    val zooSectionMemo = zooSection.value?.eMemo
    val zooSectionLink = "<a href=\"${zooSection.value?.eURL}\">${appContext.getString(R.string.link_text_view_via_browser)}</a>"

    val mPlantResultsItem = MutableLiveData<List<PlantResultsItem?>>()

    val isTopTitleShow = MutableLiveData<Boolean>().apply {
        value = false
    }

    private fun queryPlantDataBySection(sectionName:String): Observable<List<PlantResultsItem?>> {
        return dataSource.queryPlantBySection(sectionName)
    }

    init {
        zooSectionName?.let {
            disposable = queryPlantDataBySection(zooSectionName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let {
                        mPlantResultsItem.value = it
                    }
                }, { error -> Timber.e(error) })
        }
    }

    override fun onCleared() {
        super.onCleared()
        if(::disposable.isInitialized)
            disposable.dispose()
    }

}