package com.yrun.presentation.settings

import com.yrun.presentation.core.UiObservable
import javax.inject.Inject

interface SettingsUiObservable : UiObservable<SettingsUiState> {

    class Base @Inject constructor() :
        UiObservable.Abstract<SettingsUiState>(SettingsUiState.Empty),
        SettingsUiObservable
}