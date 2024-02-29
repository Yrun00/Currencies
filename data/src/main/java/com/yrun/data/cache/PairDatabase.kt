package com.yrun.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PairCache::class], version = 1, exportSchema = false)
abstract class PairDatabase : RoomDatabase() {

    abstract fun pairDao(): PairDao
}