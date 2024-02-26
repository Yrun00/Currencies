package com.yrun.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies_table")
data class CurrencyCache(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
     @ColumnInfo(name = "name")
    val name: String
)

