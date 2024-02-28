package com.yrun.currencies.modules

import com.yrun.presentation.main.CustomViewModel


interface Module<T : CustomViewModel> {

    fun viewModel(): T
}