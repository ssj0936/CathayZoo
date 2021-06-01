package com.timothy.zoo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.timothy.zoo.data.model.PlantResultsItem

class PlantDetailViewModel constructor(
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