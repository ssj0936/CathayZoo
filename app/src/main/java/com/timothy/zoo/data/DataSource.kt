package com.timothy.zoo.data

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
import timber.log.Timber
import javax.inject.Inject

class DataSource @Inject constructor(
    private val zooService: ZooService,
    private val zooSectionDao: ZooSectionDao
){

    fun queryZooSectionData(): Observable<List<ZooSectionResultsItem?>> {
        return Observable.concatArrayEager(
            zooSectionDao.getAllZooSections()
                .toObservable()
                .filter {
                     it.isNotEmpty()
                }.doOnComplete {
                    Timber.d("get from DB")
                },
            Observable.defer {
                if(isNetworkAvailable(MainApp.appContext)){
                    zooService.searchAllZooSection()
                        .subscribeOn(Schedulers.io())
                        .doOnNext{
                            Timber.d("get from API")
                        }
                        .flatMap {
                            val result = it.result.results
                            Completable.fromCallable {
                                zooSectionDao.insertZooSections(result)
                            }.andThen(Observable.just(result))
                        }
                }else{
                    empty<List<ZooSectionResultsItem>>()
                }
            }
        ).subscribeOn(Schedulers.io())
    }

    fun queryPlantBySection(sectionName:String):Observable<List<PlantResultsItem?>>{
        return Observable.concatArrayEager(
            zooSectionDao.getPlantInSection(sectionName)
                .toObservable()
                .filter {
                    it.isNotEmpty()
                }
                .doOnNext {
                    Timber.d("[DB]:${it.map {item -> item.fNameCh }}")
                }
                .doOnComplete{
                    Timber.d("[plant][$sectionName] get from DB")
                },
            Observable.defer {
                if(isNetworkAvailable(MainApp.appContext)){
                    zooService.searchPlantBySection(sectionName)
                        .subscribeOn(Schedulers.io())
                        .doOnNext{
                            Timber.d("[plant][$sectionName] get from API")
                        }
                        .flatMap {
                            //sort and remove duplicate item
                            val result = it.plantResult.results
                                .distinctBy { item -> item?.fNameLatin?.trim() }
                                .sortedBy { item -> item?.fNameCh }

                            Timber.d("[API]:${result.map {item -> item?.fNameCh}}")

                            //save into DB then emit the result
                            Completable.fromCallable {
                                zooSectionDao.insertPlant(result)
                            }.andThen(Observable.just(result))
                        }
                }else{
                    empty<List<PlantResultsItem>>()
                }
            }
        ).subscribeOn(Schedulers.io())
    }
}