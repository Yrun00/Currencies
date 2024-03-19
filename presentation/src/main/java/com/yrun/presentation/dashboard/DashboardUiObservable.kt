package com.yrun.presentation.dashboard

import com.yrun.presentation.core.UiObservable
import javax.inject.Inject

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base @Inject constructor() :
        UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty),
        DashboardUiObservable
}
