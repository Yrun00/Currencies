package com.yrun.presentation.settings

import com.yrun.presentation.core.UiObservable

interface SettingsUiObservable : UiObservable<SettingsUiState> {

    class Base : UiObservable.Abstract<SettingsUiState>(SettingsUiState.Empty),
        SettingsUiObservable
}