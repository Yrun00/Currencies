package com.yrun.presentation.core

import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.CustomViewModel

interface ProvideViewModel {

    fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel, Clear {

        private val map = HashMap<Class<out CustomViewModel>, CustomViewModel>()

        override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T =
            if (map.containsKey(viewModelClass)) {
                map[viewModelClass]
            } else {
                val viewModel = makeViewModel.viewModel(viewModelClass)
                map[viewModelClass] = viewModel
                viewModel
            } as T

        override fun clear(clazz: Class<out CustomViewModel>) {
            map.remove(clazz)
        }
    }
}