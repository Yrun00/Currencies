package com.yrun.data.dashboard.cloud

import retrofit2.Retrofit
import javax.inject.Inject

interface PairClodDataSource {

    suspend fun rate(
        toCurrency: String,
        fromCurrency: String
    ): Double

    class Base @Inject constructor(
        private val service: PairService,
    ) : PairClodDataSource {

        constructor(retrofit: Retrofit) : this(retrofit.create(PairService::class.java))

        override suspend fun rate(
            toCurrency: String,
            fromCurrency: String
        ): Double {

            return service.loadPair(toCurrency = toCurrency, fromCurrency = fromCurrency).execute()
                .body()!!.rates[toCurrency]!!
        }
    }
}
