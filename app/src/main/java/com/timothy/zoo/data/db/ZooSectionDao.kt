package com.timothy.zoo.data.db

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
    fun insertZooSections(zooSection:List<ZooSectionResultsItem?>)

    @Query("select * FROM ZooSectionResultsItem")
    fun getAllZooSections(): Single<List<ZooSectionResultsItem>>

    @Query("SELECT COUNT(*) FROM ZooSectionResultsItem")
    fun getRowNum(): Observable<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlant(plants:List<PlantResultsItem?>)

    @Query("SELECT * FROM PlantResultsItem WHERE fLocation LIKE '%' ||:sectionName|| '%' ORDER BY fNameCh ASC")
    fun getPlantInSection(sectionName:String): Single<List<PlantResultsItem>>
}