package com.timothy.zoo.di

import android.content.Context
import androidx.room.Room
import com.timothy.zoo.api.ZooService
import com.timothy.zoo.data.db.ZooDb
import com.timothy.zoo.data.db.ZooSectionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule{

    @Singleton
    @Provides
    fun provideZooSectionService() : ZooService{
        val baseURL = "https://data.taipei/"

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ZooService::class.java)
    }
}

@InstallIn(SingletonComponent::class)
@Module
object RoomModule{

    @Singleton
    @Provides
    fun provideZooDb(@ApplicationContext context: Context):ZooDb{
        val dbName = "zoo_db"
        return Room.databaseBuilder(context,ZooDb::class.java,dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideZooSectionDao(zooDb:ZooDb):ZooSectionDao{
        return zooDb.getZooSectionDao()
    }
}