package com.yrun.data

import com.yrun.data.cache.DashboardItemsDataSource
import com.yrun.data.cache.FavoritePairsCacheDataSource
import com.yrun.data.core.HandleError
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult


class BaseDashboardRepository(
    private val favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
    private val handleError: HandleError,
    private val dashboardItemsDataSource: DashboardItemsDataSource
) : DashboardRepository {
    override suspend fun dashboard(): DashboardResult {
        val favoritePairs = favoritePairsCacheDataSource.read()
        if (favoritePairs.isEmpty()) return DashboardResult.Empty
        return try {
            val updatedList = dashboardItemsDataSource.updatePairs(favoritePairs)
            DashboardResult.Success(listOfItem = updatedList)
        } catch (error: Exception) {
            DashboardResult.Error(handleError.handleError(error))
        }
    }
}