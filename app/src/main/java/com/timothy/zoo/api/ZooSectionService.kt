package com.timothy.zoo.api

import com.timothy.zoo.data.ZooSectionResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ZooSectionService {
    @GET("api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    fun searchAllZooSection() : Observable<ZooSectionResponse>

}