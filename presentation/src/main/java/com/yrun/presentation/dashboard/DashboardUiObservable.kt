package com.yrun.presentation.dashboard

import com.yrun.presentation.core.UiObservable

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base : UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty),
        DashboardUiObservable
}
