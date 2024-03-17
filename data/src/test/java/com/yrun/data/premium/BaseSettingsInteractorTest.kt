package com.yrun.data.premium

import com.yrun.domain.premium.PremiumStorage
import com.yrun.domain.premium.SaveResult
import com.yrun.domain.premium.SettingsInteractor
import com.yrun.domain.settings.SettingsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BaseSettingsInteractorTest {

    private lateinit var interactor: SettingsInteractor.Base
    private lateinit var repository: FakeSettingsRepository
    private lateinit var premiumStorage: FakePremiumStorage
    private val maxFreePairsCount: Int = 2

    @Before
    fun setup() {

        repository = FakeSettingsRepository()
        premiumStorage = FakePremiumStorage()
        interactor = SettingsInteractor.Base(repository, premiumStorage, maxFreePairsCount)
    }

    @Test
    fun allCurrenciesTest() = runBlocking {
        interactor.allCurrencies()
        assertTrue(repository.allCurrenciesCalled)
    }

    @Test
    fun availableDestinationsTest() = runBlocking {
        interactor.availableDestinations("")
        assertTrue(repository.availableDestinationsCalled)
    }

    @Test
    fun saveTest() = runBlocking {
        premiumStorage.premium = true
        assertEquals(SaveResult.Success, interactor.save("", ""))
        assertEquals(1, repository.savedPairs)

        premiumStorage.premium = false
        assertEquals(SaveResult.Success, interactor.save("", ""))
        assertEquals(2, repository.savedPairs)

        assertEquals(SaveResult.RequirePremium, interactor.save("", ""))
        assertEquals(2, repository.savedPairs)

        premiumStorage.premium = true
        assertEquals(SaveResult.Success, interactor.save("", ""))
        assertEquals(3, repository.savedPairs)
    }
}

class FakePremiumStorage : PremiumStorage.Read {

    var premium = false

    override fun havePremium(): Boolean {
        return premium
    }
}

class FakeSettingsRepository : SettingsRepository {

    var allCurrenciesCalled = false
    var availableDestinationsCalled = false
    var savedPairs = 0

    override suspend fun allCurrencies(): List<String> {
        allCurrenciesCalled = true
        return listOf("USD", "EUR")
    }

    override suspend fun availableDestinations(fromCurrency: String): List<String> {
        availableDestinationsCalled = true
        return listOf("USD")
    }

    override suspend fun save(toCurrency: String, fromCurrency: String) {
        savedPairs++
    }

    override suspend fun savedPairsCount(): Int {
        return savedPairs
    }
}