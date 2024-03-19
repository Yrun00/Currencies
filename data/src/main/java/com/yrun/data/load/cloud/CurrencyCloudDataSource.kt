package com.yrun.data.load.cloud

import retrofit2.Retrofit
import javax.inject.Inject

interface CurrencyCloudDataSource {

    suspend fun currencies(): HashMap<String, String>

    class Base @Inject constructor(
        private val service: CurrencyService,
    ) : CurrencyCloudDataSource {

        constructor(retrofit: Retrofit) : this(retrofit.create(CurrencyService::class.java))

        override suspend fun currencies(): HashMap<String, String> {
            val result = service.loadCurrencies().execute()
            return result.body()!!
        }
    }
}