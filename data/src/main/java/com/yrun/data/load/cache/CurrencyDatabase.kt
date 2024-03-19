package com.yrun.data.load.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.data.dashboard.cache.PairDao


@Database(entities = [CurrencyCache::class, PairCache::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun pairDao(): PairDao
}