package com.yrun.domain.settings

interface SettingsRepository {

    suspend fun allCurrencies(): List<String>

    suspend fun availableDestinations(fromCurrency: String): List<String>

    suspend fun save(toCurrency: String, fromCurrency: String)

    suspend fun savedPairsCount(): Int
}