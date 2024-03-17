package com.yrun.domain.dashboard

interface DashboardRepository {

    suspend fun dashboard(): DashboardResult

    suspend fun deletePair(from: String, to: String): DashboardResult
}