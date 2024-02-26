package com.yrun.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yrun.data.cache.CurrencyCache
import com.yrun.data.cache.CurrencyDao
import com.yrun.data.cache.CurrencyDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: CurrencyDatabase
    private lateinit var dao: CurrencyDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, CurrencyDatabase::class.java
        )
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.currencyDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test() = runBlocking {
        assertEquals(emptyList<CurrencyCache>(), dao.currencies())
        val expected = listOf(
            CurrencyCache("111", "1111111"),
            CurrencyCache("222", "2222222"),
            CurrencyCache("333", "3333333")
        )
        dao.insert(expected)
        assertEquals(expected, dao.currencies())
    }
}