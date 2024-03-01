package com.yrun.data.dashboard.cloud

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface PairClodDataSource {

    suspend fun rate(
        toCurrency: String,
        fromCurrency: String
    ): Double

    class Base(
        private val service: PairService,
    ) : PairClodDataSource {

        constructor() : this(
            Retrofit.Builder().baseUrl("https://api.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(PairService::class.java)
        )

        override suspend fun rate(

            toCurrency: String,
            fromCurrency: String
        ): Double {

            return service.loadPair(toCurrency = toCurrency, fromCurrency = fromCurrency).execute()
                .body()!!.rates[toCurrency]!!
        }
    }
}
