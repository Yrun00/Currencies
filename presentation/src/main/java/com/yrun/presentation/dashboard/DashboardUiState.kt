package com.yrun.presentation.dashboard

interface DashboardUiState {

    fun update(showList: ShowList)

    abstract class Abstract(private val dashboardUi: DashboardUi) : DashboardUiState {

        override fun update(showList: ShowList) {
            showList.show(listOf(dashboardUi))
        }
    }

    data class Base(private val pairs: List<DashboardUi>) : DashboardUiState {

        override fun update(showList: ShowList) {
            showList.show(pairs)
        }
    }

    data class Error(private val message: String) : Abstract(DashboardUi.Error(message))

    object Empty : Abstract(DashboardUi.Empty)

    object Progress : Abstract(DashboardUi.Progress)
}