package com.timothy.zoo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
class MainViewModel @Inject constructor(
    private val dataSource: DataSource
):ViewModel() {
    var disposable:Disposable
    val mZooSectionResultsItem = MutableLiveData<List<ZooSectionResultsItem?>>()

    private fun queryZooSectionData(): Observable<List<ZooSectionResultsItem?>> {
        return dataSource.queryZooSectionData()
    }

    init {
        disposable = queryZooSectionData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    mZooSectionResultsItem.value = it
                }
            },{error-> Timber.e(error)})
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}