package com.yrun.presentation.load

import com.yrun.domain.load.LoadCurrenciesRepository
import com.yrun.domain.load.LoadCurrenciesResult

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
