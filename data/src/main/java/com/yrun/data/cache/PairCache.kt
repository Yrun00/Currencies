package com.yrun.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorite_pairs_table", primaryKeys = ["toCurrency", "fromCurrency"])
data class PairCache(
    @ColumnInfo(name = "toCurrency")
    val toCurrency: String,
    @ColumnInfo(name = "fromCurrency")
    val fromCurrency: String,
    @ColumnInfo(name = "rate")
    val rate: Double = 0.0,
    @ColumnInfo(name = "time")
    val time: Long = -2
) {
    fun isInvalid(currentTimeInMillis: CurrentTimeInMillis): Boolean {
        return time + (24 * 3600 * 1000) < currentTimeInMillis.currentTime() || rate == 0.0
    }
}