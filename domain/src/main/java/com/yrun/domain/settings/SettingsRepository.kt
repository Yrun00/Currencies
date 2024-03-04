package com.yrun.domain.settings

interface SettingsRepository {

    suspend fun allCurrencies(): List<String>

    suspend fun availableDestinations(fromCurrency: String): List<String>

    suspend fun save(fromCurrency: String, toCurrency: String)
}