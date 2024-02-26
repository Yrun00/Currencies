package com.yrun.data

import com.yrun.data.cloud.CurrencyCloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class CurrencyCloudDataSourceTest {

    @Test
    fun serviceTest() = runBlocking {

        val actual = CurrencyCloudDataSource.Base().currencies()
        assertEquals(true, actual.isNotEmpty())

    }
}