package com.yrun.presentation

import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.Screen

class FakeNavigation : Navigation.Mutable {

    var actual: Screen = Screen.Empty
    var observer: UpdateUi<Screen> = UpdateUi.Empty()


    override fun updateObserver(observer: UpdateUi<Screen>) {
        this.observer = observer
    }

    override fun updateUi(uiState: Screen) {
        actual = uiState
    }
}