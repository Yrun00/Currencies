package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.currencies.ProvideInstance
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.presentation.main.Clear
import com.yrun.presentation.settings.SettingsUiObservable
import com.yrun.presentation.settings.SettingsViewModel

class SettingsModule(
    private val core: Core,
    private val clear: Clear,
    private val provideInstance: ProvideInstance

) : Module<SettingsViewModel> {

    override fun viewModel(): SettingsViewModel {
        return SettingsViewModel(
            navigation = core.provideNavigation(),
            runAsync = core.provideRunAsync(),
            clear = clear,
            observable = SettingsUiObservable.Base(),
            repository = provideInstance.provideSettingsRepository(
                core.provideCurrencyDataBase().currencyDao(),
                favoritePairsCacheDataSource = FavoritePairsCacheDataSource.Base(
                    core.providePairDatabase().pairDao()
                )
            )
        )
    }
}