package com.yrun.data

import com.yrun.data.cloud.CurrencyCloudDataSource
import com.yrun.data.cloud.PairClodDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class CurrencyCloudDataSourceTest {

    @Test
    fun currencyServiceTest() = runBlocking {

        val actual = CurrencyCloudDataSource.Base().currencies()
        assertEquals(true, actual.isNotEmpty())

    }

    @Test
    fun pairServiceTest() = runBlocking {
        val actual = PairClodDataSource.Base().rate("USD", "EUR")
        assertEquals(1.0, actual, 0.15)
    }
}