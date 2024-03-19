package com.yrun.presentation.load

import com.yrun.presentation.core.UiObservable
import javax.inject.Inject

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base @Inject constructor() : UiObservable.Abstract<LoadUiState>(LoadUiState.Empty),
        LoadUiObservable

}