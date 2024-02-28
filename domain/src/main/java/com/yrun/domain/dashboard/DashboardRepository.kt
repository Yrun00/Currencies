package com.yrun.domain.dashboard

interface DashboardRepository {

    suspend fun dashboard(): DashboardResult
}