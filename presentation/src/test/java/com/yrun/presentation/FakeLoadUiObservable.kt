package com.yrun.presentation

import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.load.LoadUiState

class FakeLoadUiObservable : UiObservable<LoadUiState> {

    var actual: LoadUiState = LoadUiState.Empty
    var observer: UpdateUi<LoadUiState> = UpdateUi.Empty()

    override fun updateObserver(observer: UpdateUi<LoadUiState>) {
        this.observer = observer
    }

    override fun updateUi(uiState: LoadUiState) {
        actual = uiState
    }

}
