package com.yrun.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.data.dashboard.cache.PairDao
import com.yrun.data.dashboard.cache.PairDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomTestPairDao {

    private lateinit var db: PairDatabase
    private lateinit var dao: PairDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, PairDatabase::class.java
        )
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.pairDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test() = runBlocking {
        assertEquals(emptyList<PairCache>(), dao.favoritePairs())
        val expected1 = PairCache("A", "B", 0.0, 23 * 3600 * 1000)
        dao.insert(expected1)
        assertEquals(listOf<PairCache>(expected1), dao.favoritePairs())
        val expected2 = PairCache("C", "D", 1.0, 25 * 3600 * 1000)
        dao.insert(expected2)
        assertEquals(listOf<PairCache>(expected1, expected2), dao.favoritePairs())
    }
}