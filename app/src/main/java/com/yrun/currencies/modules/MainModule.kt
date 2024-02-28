package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.presentation.main.MainViewModel

class MainModule(
    private val core: Core,
) : Module<MainViewModel> {

    override fun viewModel() = MainViewModel(
        navigation = core.provideNavigation()
    )
}