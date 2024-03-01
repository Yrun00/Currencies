package com.yrun.data.dashboard.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PairDao {

    @Query("SELECT * FROM favorite_pairs_table")
    suspend fun favoritePairs(): List<PairCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyPair: PairCache)
}