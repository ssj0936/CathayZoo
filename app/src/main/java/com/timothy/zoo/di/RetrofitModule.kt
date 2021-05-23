package com.timothy.zoo.di

import com.timothy.zoo.api.ZooSectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule{

    @Singleton
    @Provides
    fun provideZooSectionService() : ZooSectionService{
        val baseURL = "https://data.taipei/"

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .client(httpClient)
            .build()
            .create(ZooSectionService::class.java)
    }
}