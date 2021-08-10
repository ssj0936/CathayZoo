package com.timothy.zoo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.data.model.ZooSectionResultsItem
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ZooSectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZooSections(zooSection:List<ZooSectionResultsItem?>)

    @Query("select * FROM ZooSectionResultsItem")
    fun getAllZooSections(): LiveData<List<ZooSectionResultsItem?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plants:List<PlantResultsItem?>)

    @Query("SELECT * FROM PlantResultsItem WHERE fLocation LIKE '%' ||:sectionName|| '%' ORDER BY fNameCh ASC")
    fun getPlantInSection(sectionName:String): LiveData<List<PlantResultsItem?>>
}