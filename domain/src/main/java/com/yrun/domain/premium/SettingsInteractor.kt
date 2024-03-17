package com.yrun.domain.premium

import com.yrun.domain.settings.SettingsRepository

interface SettingsInteractor {

    suspend fun allCurrencies(): List<String>

    suspend fun availableDestinations(fromCurrency: String): List<String>

    suspend fun save(toCurrency: String, fromCurrency: String): SaveResult

    class Base(
        private val repository: SettingsRepository, private val premiumStorage: PremiumStorage.Read,
        private val maxFreePairsCount: Int
    ) : SettingsInteractor {
        override suspend fun allCurrencies(): List<String> {
            return repository.allCurrencies()
        }

        override suspend fun availableDestinations(fromCurrency: String): List<String> {
            return repository.availableDestinations(fromCurrency)
        }

        override suspend fun save(toCurrency: String, fromCurrency: String): SaveResult =
            if (premiumStorage.havePremium() || repository.savedPairsCount() < maxFreePairsCount) {
                repository.save(fromCurrency = fromCurrency, toCurrency = toCurrency)
                SaveResult.Success
            } else
                SaveResult.RequirePremium
    }
}