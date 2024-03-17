package com.yrun.presentation.dashboard

import com.yrun.presentation.core.ShowList
import com.yrun.presentation.dashboard.adapter.DashboardUi

interface DashboardUiState {

    fun update(showList: ShowList<DashboardUi>)

    abstract class Abstract(private val dashboardUi: DashboardUi) : DashboardUiState {

        override fun update(showList: ShowList<DashboardUi>) {
            showList.show(listOf(dashboardUi))
        }
    }

    data class Base(private val pairs: List<DashboardUi>) : DashboardUiState {

        override fun update(showList: ShowList<DashboardUi>) {
            showList.show(pairs)
        }
    }

    data class Error(private val message: String) : Abstract(DashboardUi.Error(message))

    object Empty : Abstract(DashboardUi.Empty)

    object Progress : Abstract(DashboardUi.Progress)
}