package com.yrun.data.dashboard

import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
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

    override suspend fun deletePair(from: String, to: String): DashboardResult {
        favoritePairsCacheDataSource.delete(
            favoritePairsCacheDataSource.read().find {
                it.fromCurrency == from && it.toCurrency == to
            }!!
        )
        return dashboard()
    }
}