package com.timothy.zoo.api

import com.timothy.zoo.data.model.PlantResponse
import com.timothy.zoo.data.model.ZooSectionResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ZooService {
//    @GET("api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
//    fun searchAllZooSection() : Observable<ZooSectionResponse>

    @GET("api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    suspend fun searchAllZooSection() : ZooSectionResponse


    @GET("api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire&q=section")
    suspend fun searchPlantBySection(
        @Query("q") section:String,
        @Query("scope") scope:String = "resourceAquire"
    ) : PlantResponse

}