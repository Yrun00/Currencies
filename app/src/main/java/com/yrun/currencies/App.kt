package com.yrun.currencies

import android.app.Application
import com.yrun.currencies.modules.ProvideModule
import com.yrun.presentation.core.ProvideViewModel
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.CustomViewModel

abstract class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val clear = object : Clear {
            override fun clear(clazz: Class<out CustomViewModel>) {
                factory.clear(clazz)
            }

        }
        factory = ProvideViewModel.Factory(
            BaseProvideViewModel(
                ProvideModule.Base(provideInstance(), Core.Base(this), clear)
            )
        )
    }

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)

    abstract fun provideInstance(): ProvideInstance

}

class Release : App() {

    override fun provideInstance(): ProvideInstance {
        return ProvideInstance.Base()
    }
}
