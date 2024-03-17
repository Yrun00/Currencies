package com.yrun.data.load.cloud

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CurrencyCloudDataSource {

    suspend fun currencies(): HashMap<String, String>

    class Base(
        private val service: CurrencyService,
    ) : CurrencyCloudDataSource {

        constructor() : this(
            Retrofit.Builder().baseUrl("https://api.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(CurrencyService::class.java)
        )

        override suspend fun currencies(): HashMap<String, String> {
            val result = service.loadCurrencies().execute()
            return result.body()!!
        }
    }
}