package com.yrun.data.dashboard

import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.data.load.cloud.CurrencyService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CurrencyCloudDataSourceTest {

    @Test
    fun currencyServiceTest() = runBlocking {
        val actual = CurrencyCloudDataSource.Base(
            Retrofit.Builder()
                .baseUrl("https://www.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                ).build().create(CurrencyService::class.java)
        ).currencies()
        assertEquals(true, actual.isNotEmpty())
    }
}
