package com.yrun.domain.load

interface LoadCurrenciesRepository {

    suspend fun loadCurrencies(): LoadCurrenciesResult

}

