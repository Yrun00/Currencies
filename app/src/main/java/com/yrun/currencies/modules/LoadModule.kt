package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.currencies.ProvideInstance
import com.yrun.data.cache.CurrencyCacheDataSource
import com.yrun.data.cloud.CurrencyCloudDataSource
import com.yrun.presentation.core.LoadViewModel
import com.yrun.presentation.load.LoadUiObservable
import com.yrun.presentation.main.Clear

class LoadModule(
    private val core: Core,
    private val provideInstance: ProvideInstance,
    private val clear: Clear

) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        return LoadViewModel(
            uiObservable = LoadUiObservable.Base(),
            repository = provideInstance.provideLoadRepository(
                provideResources = core.provideResources(),
                cacheDataSource = CurrencyCacheDataSource.Base(
                    core.provideDataBase().currencyDao()
                ),
                cloudDataSource = CurrencyCloudDataSource.Base()
            ),
            clear = clear,
            navigation = core.provideNavigation(),
            runAsync = core.provideRunAsync()
        )
    }
}