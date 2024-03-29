package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.currencies.ProvideInstance
import com.yrun.presentation.dashboard.DashboardViewModel
import com.yrun.presentation.load.LoadViewModel
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.CustomViewModel
import com.yrun.presentation.main.MainViewModel
import com.yrun.presentation.premium.PremiumViewModel
import com.yrun.presentation.settings.SettingsViewModel


interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val provideInstance: ProvideInstance.ProvideRepository,
        private val core: Core,
        private val clear: Clear
    ) : ProvideModule {

        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core = core)

                LoadViewModel::class.java -> LoadModule(
                    core = core,
                    clear = clear,
                    provideInstance = provideInstance
                )

                DashboardViewModel::class.java -> DashboardModule(
                    core = core,
                    clear = clear,
                    provideInstance = provideInstance
                )

                SettingsViewModel::class.java -> SettingsModule(
                    core = core,
                    clear = clear,
                    provideInstance = provideInstance
                )

                PremiumViewModel::class.java -> PremiumModule(
                    core = core,
                    clear = clear,
                    provideInstance = provideInstance
                )

                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}