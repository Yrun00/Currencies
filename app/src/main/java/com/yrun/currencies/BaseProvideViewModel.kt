package com.yrun.currencies

import com.yrun.currencies.modules.ProvideModule
import com.yrun.presentation.core.ProvideViewModel
import com.yrun.presentation.main.CustomViewModel


class BaseProvideViewModel(private val provideModule: ProvideModule) : ProvideViewModel {

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return provideModule.module(viewModelClass).viewModel()
    }
}