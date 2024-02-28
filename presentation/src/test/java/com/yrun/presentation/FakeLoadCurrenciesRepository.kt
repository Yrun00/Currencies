package com.yrun.presentation

import com.yrun.domain.LoadCurrenciesRepository
import com.yrun.domain.LoadCurrenciesResult

class FakeLoadCurrenciesRepository : LoadCurrenciesRepository {

    private lateinit var result: LoadCurrenciesResult

    fun success() {
        result = LoadCurrenciesResult.Success
    }

    fun error() {
        result = LoadCurrenciesResult.Error("No internet")
    }

    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        return result
    }
}
