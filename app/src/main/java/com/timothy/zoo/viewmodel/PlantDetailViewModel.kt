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


class PlantDetailViewModel constructor(
//    private val dataSource: DataSource,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val plantInfo = savedStateHandle.get<PlantResultsItem>("plant_info")!!
    val plantPicUrl = plantInfo.fPic01URL
    val plantNameEn = plantInfo.fNameEn
    val plantNameCh = plantInfo.fNameCh
    val plantNameLatin = plantInfo.fNameLatin
    val plantFamily = plantInfo.fFamily
    val plantGenus = plantInfo.fGenus

    val plantAlsoKnown = plantInfo.fAlsoKnown
    val plantFeature = plantInfo.fFeature
    val plantFunctionApplication = plantInfo.fFunctionApplication
}