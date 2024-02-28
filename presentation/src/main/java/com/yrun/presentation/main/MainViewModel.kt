package com.yrun.presentation.main

import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.load.LoadScreen

class MainViewModel(
    private val navigation: Navigation
) : CustomViewModel {


    fun init(isFirsRun: Boolean) {
        if (isFirsRun)
            navigation.updateUi(LoadScreen)
    }

    fun startGettingUpdates(navigation: UpdateUi<Screen>) {
        this.navigation.updateObserver(navigation)
    }

    fun stopGettingUpdates() {
        navigation.updateObserver(UpdateUi.Empty())
    }
}
