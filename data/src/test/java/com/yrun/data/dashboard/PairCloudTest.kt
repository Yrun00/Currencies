package com.yrun.data.dashboard

import com.yrun.data.dashboard.cloud.PairClodDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PairCloudTest {

    @Test
    fun pairServiceTest() = runBlocking {
        val actual = PairClodDataSource.Base().rate("USD", "EUR")
        Assert.assertEquals(1.0, actual, 0.15)
    }
}
