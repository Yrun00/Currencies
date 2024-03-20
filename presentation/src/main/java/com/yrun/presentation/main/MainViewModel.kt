package com.yrun.presentation.main

import androidx.lifecycle.ViewModel
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.load.LoadScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation.Mutable
) : ViewModel() {

    override fun onCleared() {
        log("MainViewModel destroyed")
        super.onCleared()
    }

    init {
        log("MainViewModel created")
    }


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
