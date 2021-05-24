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
    private val zooSection = savedStateHandle.get<ZooSectionResultsItem>("zoo_section")!!
    val zooSectionName = zooSection.eName
    val zooSectionDesc = zooSection.eInfo
    val zooSectionPicUrl = zooSection.ePicURL
    val zooSectionCategory = zooSection.eCategory
    val zooSectionMemo = zooSection.eMemo
    val zooSectionLink = "<a href=\"${zooSection.eURL}\">${appContext.getString(R.string.link_text_view_via_browser)}</a>"

    val mPlantResultsItem = MutableLiveData<List<PlantResultsItem?>>()

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