package com.yrun.presentation.settings

import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.premium.BasePremiumResultMapper
import com.yrun.presentation.premium.PremiumScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BasePremiumResultMapperTest {

    private lateinit var navigation: FakeNavigation
    private lateinit var mapper: BasePremiumResultMapper

    @Before
    fun setup() {
        navigation = FakeNavigation()
        mapper = BasePremiumResultMapper(navigation)
    }

    @Test
    fun successfullyTest() {
        mapper.mapSuccessful()
        assertEquals(DashboardScreen, navigation.actual)
    }

    @Test
    fun requirePremiumTest() {
        mapper.mapRequirePremium()
        assertEquals(PremiumScreen, navigation.actual)
    }

}