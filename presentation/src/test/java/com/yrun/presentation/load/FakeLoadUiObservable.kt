package com.yrun.presentation.load

import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.core.UpdateUi

class FakeLoadUiObservable : UiObservable<LoadUiState>, LoadUiObservable {

    var actual: LoadUiState = LoadUiState.Empty
    var observer: UpdateUi<LoadUiState> = UpdateUi.Empty()

    override fun updateObserver(observer: UpdateUi<LoadUiState>) {
        this.observer = observer
    }

    override fun updateUi(uiState: LoadUiState) {
        actual = uiState
    }

}
