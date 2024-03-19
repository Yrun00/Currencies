package com.yrun.data.dashboard

import com.yrun.data.dashboard.cloud.PairClodDataSource
import com.yrun.data.dashboard.cloud.PairService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PairCloudTest {

    @Test
    fun pairServiceTest() = runBlocking {
        val actual = PairClodDataSource.Base(
            Retrofit.Builder()
                .baseUrl("https://www.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                ).build().create(PairService::class.java)
        ).rate("USD", "EUR")
        Assert.assertEquals(1.0, actual, 0.15)
    }
}
