package com.yrun.data.dashboard

import com.yrun.data.dashboard.cache.PairCache
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PairCacheTest {

    private lateinit var pairCache: PairCache
    private lateinit var time: FakeCurrentTimeInMillis

    @Before
    fun setup() {
        time = FakeCurrentTimeInMillis()
    }

    @Test
    fun invalidTimeAndRate() = runBlocking {
        pairCache = PairCache("A", "B", 0.0, 23 * 3600 * 1000)
        Assert.assertEquals(true, pairCache.isInvalid(time))
    }

    @Test
    fun invalidTime() = runBlocking {
        pairCache = PairCache("C", "D", 1.0, 23 * 3600 * 1000)
        Assert.assertEquals(true, pairCache.isInvalid(time))
    }

    @Test
    fun invalidRate() = runBlocking {
        pairCache = PairCache("E", "F", 0.0, 47 * 3600 * 1000)
        Assert.assertEquals(true, pairCache.isInvalid(time))
    }

    @Test
    fun valid() = runBlocking {
        pairCache = PairCache("Q", "W", 1.0, 47 * 3600 * 1000)
        Assert.assertEquals(false, pairCache.isInvalid(time))
    }

}