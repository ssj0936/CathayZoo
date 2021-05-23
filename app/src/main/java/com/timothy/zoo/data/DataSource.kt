package com.timothy.zoo.data

import com.timothy.zoo.MainApp
import com.timothy.zoo.api.ZooSectionService
import com.timothy.zoo.data.db.ZooSectionDao
import com.timothy.zoo.data.model.ZooSectionResultsItem
import com.timothy.zoo.utils.isNetworkAvailable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.empty
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DataSource @Inject constructor(
    private val zooSectionService: ZooSectionService,
    private val zooSectionDao: ZooSectionDao
){

    fun queryZooSectionData(): Observable<List<ZooSectionResultsItem?>> {
        return Observable.concatArrayEager(
            zooSectionDao.getAllZooSections()
                .toObservable()
                .doOnComplete {
                    Timber.d("get from DB")
                },
            Observable.defer {
                if(isNetworkAvailable(MainApp.appContext)){
                    zooSectionService.searchAllZooSection()
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
}