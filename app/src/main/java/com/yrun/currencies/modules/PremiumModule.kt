package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.currencies.ProvideInstance
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.presentation.main.Clear
import com.yrun.presentation.premium.PremiumViewModel

class PremiumModule(
    private val clear: Clear,
    private val core: Core,
    private val provideInstance: ProvideInstance.ProvideRepository

) : Module<PremiumViewModel> {
    override fun viewModel(): PremiumViewModel {
        return PremiumViewModel(
            clear = clear,
            navigation = core.provideNavigation(),
            premiumStorage = core.providePremiumStorage(),
            interactor = provideInstance.provideSettingsInteractor(
                core.provideCurrencyDataBase().currencyDao(),
                premiumStorage = core.providePremiumStorage(),
                maxFreePairCount = provideInstance.provideMaxFreeSavedPairsCount(),
                favoritePairsCacheDataSource = FavoritePairsCacheDataSource.Base(
                    core.providePairDatabase().pairDao()
                )
            ), runAsync = core.provideRunAsync()
        )
    }
}