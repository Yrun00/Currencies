package com.yrun.presentation.load

import com.yrun.presentation.core.UiObservable

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(LoadUiState.Empty)

}