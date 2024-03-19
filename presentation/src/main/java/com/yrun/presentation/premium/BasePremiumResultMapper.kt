package com.yrun.presentation.premium

import com.yrun.domain.premium.SaveResult
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.main.Navigation
import javax.inject.Inject

class BasePremiumResultMapper @Inject constructor(
    private val navigation: Navigation.Mutable,
) : SaveResult.Mapper {
    override fun mapSuccessful() {
        navigation.updateUi(DashboardScreen)
    }

    override fun mapRequirePremium() {
        navigation.updateUi(PremiumScreen)
    }
}