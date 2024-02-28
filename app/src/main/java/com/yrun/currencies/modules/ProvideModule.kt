package com.yrun.currencies.modules

import com.yrun.currencies.Core
import com.yrun.currencies.ProvideInstance
import com.yrun.presentation.core.LoadViewModel
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.CustomViewModel
import com.yrun.presentation.main.MainViewModel


interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val provideInstance: ProvideInstance,
        private val core: Core,
        private val clear: Clear
    ) : ProvideModule {

        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core)
                LoadViewModel::class.java -> LoadModule(
                    core,
                    clear = clear,
                    provideInstance = provideInstance
                )

                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}