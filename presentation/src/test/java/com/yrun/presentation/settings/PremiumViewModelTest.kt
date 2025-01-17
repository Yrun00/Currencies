package com.yrun.presentation.settings

import com.yrun.domain.premium.PremiumStorage
import com.yrun.domain.premium.SaveResult
import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.FakeRunAsync
import com.yrun.presentation.main.Screen
import com.yrun.presentation.premium.PremiumViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PremiumViewModelTest {

    private lateinit var viewModel: PremiumViewModel
    private lateinit var interactor: FakeSettingsInteractor
    private lateinit var navigation: FakeNavigation
    private lateinit var runAsync: FakeRunAsync
    private lateinit var premiumStorage: FakePremiumStorage
    private lateinit var mapper: FakePremiumResultMapper


    @Before
    fun setup() {
        interactor = FakeSettingsInteractor()
        navigation = FakeNavigation()
        runAsync = FakeRunAsync()
        premiumStorage = FakePremiumStorage()
        mapper = FakePremiumResultMapper()
        viewModel = PremiumViewModel(
            interactor = interactor,
            navigation = navigation,
            runAsync = runAsync,
            premiumMapper = mapper,
            premiumStorage = premiumStorage
        )
    }

    @Test
    fun testBack() {
        viewModel.backToSettings()
        assertEquals(Screen.Pop, navigation.actual)
    }

    @Test
    fun testBuyAndGoToDashboard() {
        viewModel.buyPremiumAndGoToDashboard(pair = Pair("USD", "EUR"))
        assertTrue(premiumStorage.havePremium())
        interactor.checkSaveCalled()
        runAsync.returnResult()
        assertTrue(mapper.mapSuccessfulCalled)
    }

}

class FakePremiumResultMapper : SaveResult.Mapper {

    var mapSuccessfulCalled = false
    var mapRequirePremiumCalled = false

    override fun mapSuccessful() {
        mapSuccessfulCalled = true
    }

    override fun mapRequirePremium() {
        mapRequirePremiumCalled = true
    }

}

private class FakePremiumStorage : PremiumStorage.Mutable {

    var premium = false


    override fun havePremium(): Boolean {
        return premium
    }

    override fun savePremium() {
        premium = true
    }

}