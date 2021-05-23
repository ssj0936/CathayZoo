package com.timothy.zoo.data

import com.timothy.zoo.api.ZooSectionService
import com.timothy.zoo.data.model.ZooSectionResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DataSource @Inject constructor(
    private val zooSectionService: ZooSectionService
){

    fun queryZooSectionData():Single<ZooSectionResponse>{
        return zooSectionService.searchAllZooSection()
            .observeOn(Schedulers.newThread())
    }
}