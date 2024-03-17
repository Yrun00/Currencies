package com.yrun.presentation.settings

import com.yrun.presentation.FakeClear
import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.premium.BasePremiumResultMapper
import com.yrun.presentation.premium.PremiumScreen
import com.yrun.presentation.premium.PremiumViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BasePremiumResultMapperTest {

    private lateinit var clear: FakeClear
    private lateinit var navigation: FakeNavigation
    private lateinit var mapper: BasePremiumResultMapper

    @Before
    fun setup() {
        clear = FakeClear()
        navigation = FakeNavigation()
        mapper = BasePremiumResultMapper(navigation, clear)
    }

    @Test
    fun successfullyTest() {
        mapper.mapSuccessful()
        assertEquals(DashboardScreen, navigation.actual)
        clear.checkList(
            listOf(
                SettingsViewModel::class.java.toString(),
                PremiumViewModel::class.java.toString()
            )
        )
    }

    @Test
    fun requirePremiumTest() {
        mapper.mapRequirePremium()
        assertEquals(PremiumScreen, navigation.actual)
        clear.clearNotCalled()
    }

}