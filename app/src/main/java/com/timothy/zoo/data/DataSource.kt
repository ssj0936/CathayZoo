package com.timothy.zoo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.timothy.zoo.MainApp
import com.timothy.zoo.api.ZooService
import com.timothy.zoo.data.db.ZooSectionDao
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.data.model.ZooSectionResultsItem
import com.timothy.zoo.utils.isNetworkAvailable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.empty
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DataSource @Inject constructor(
    private val zooService: ZooService,
    private val zooSectionDao: ZooSectionDao
){
    fun queryZooSectionData():LiveData<List<ZooSectionResultsItem?>> = liveData(Dispatchers.IO) {
        emitSource(zooSectionDao.getAllZooSections())

        val resultApi = zooService.searchAllZooSection().result.results

        if(resultApi.isNullOrEmpty()){
            emit(emptyList<ZooSectionResultsItem>())
        }else{
            zooSectionDao.insertZooSections(resultApi)
        }
    }


    fun queryPlantBySection(sectionName:String):LiveData<List<PlantResultsItem?>> = liveData(Dispatchers.IO){
        emitSource(zooSectionDao.getPlantInSection(sectionName))

        val resultApi = zooService.searchPlantBySection(sectionName).plantResult.results
        if(resultApi.isNullOrEmpty()){
            emit(emptyList<PlantResultsItem>())
        }else{
            resultApi.apply {
                distinctBy { item -> item?.fNameLatin?.trim() }
                sortedBy { item -> item?.fNameCh }
            }
            zooSectionDao.insertPlant(resultApi)
        }
    }
}