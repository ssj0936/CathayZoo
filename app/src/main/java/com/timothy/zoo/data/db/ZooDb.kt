package com.timothy.zoo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timothy.zoo.data.model.PlantResultsItem
import com.timothy.zoo.data.model.ZooSectionResultsItem

@Database(entities = [ZooSectionResultsItem::class, PlantResultsItem::class], version = 4,exportSchema = false)
abstract class ZooDb: RoomDatabase() {
    abstract fun getZooSectionDao():ZooSectionDao
}