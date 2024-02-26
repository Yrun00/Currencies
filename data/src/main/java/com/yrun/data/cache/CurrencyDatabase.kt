package com.yrun.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CurrencyCache::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}