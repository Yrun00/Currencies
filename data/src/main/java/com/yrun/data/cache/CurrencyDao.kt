package com.yrun.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currencies_table")
    suspend fun currencies(): List<CurrencyCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyList: List<CurrencyCache>)
}