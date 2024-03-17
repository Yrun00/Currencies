package com.yrun.presentation.premium

import com.yrun.domain.premium.SaveResult
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.settings.SettingsViewModel

class BasePremiumResultMapper(
    private val navigation: Navigation.Navigate,
    private val clear: Clear
) : SaveResult.Mapper {
    override fun mapSuccessful() {
        clear.clear(SettingsViewModel::class.java)
        clear.clear(PremiumViewModel::class.java)
        navigation.updateUi(DashboardScreen)
    }

    override fun mapRequirePremium() {
        navigation.updateUi(PremiumScreen)
    }
}