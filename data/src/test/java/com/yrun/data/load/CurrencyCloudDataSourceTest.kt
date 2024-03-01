package com.yrun.data.load

import com.yrun.data.load.cloud.CurrencyCloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class CurrencyCloudDataSourceTest {

    @Test
    fun currencyServiceTest() = runBlocking {
        val actual = CurrencyCloudDataSource.Base().currencies()
        assertEquals(true, actual.isNotEmpty())
    }
}
