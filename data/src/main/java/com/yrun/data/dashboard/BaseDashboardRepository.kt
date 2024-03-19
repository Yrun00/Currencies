package com.yrun.data.dashboard

import com.yrun.data.core.CurrentTimeInMillis
import com.yrun.data.core.HandleError
import com.yrun.data.dashboard.cache.DashboardItemsDataSource
import com.yrun.data.dashboard.cache.FavoritePairsCacheDataSource
import com.yrun.data.dashboard.cache.PairCache
import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import java.net.UnknownHostException
import javax.inject.Inject


class BaseDashboardRepository @Inject constructor(
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

class FakeDashboardRepository(
    private val favoritePairsCacheDataSource: FavoritePairsCacheDataSource.Mutable,
    private val handleError: HandleError,
    private val dashboardItemsDataSource: DashboardItemsDataSource
) : DashboardRepository {

    override suspend fun dashboard(): DashboardResult {


        if (firstTime) {
            firstTime = false
            return DashboardResult.Error(handleError.handleError(UnknownHostException()))
        } else {
            val currentTimeInMillis = CurrentTimeInMillis.Base()

            suspend fun updatePairs(favoritePairs: List<PairCache>): List<DashboardItem> =
                favoritePairs.map { currentPair ->
                    val rate = if (currentPair.isInvalid(currentTimeInMillis)) {
                        count += 1.0
                        val updatePair = PairCache(
                            currentPair.toCurrency,
                            currentPair.fromCurrency,
                            count,
                            currentTimeInMillis.currentTime()
                        )
                        favoritePairsCacheDataSource.save(updatePair)
                        count
                    } else currentPair.rate

                    DashboardItem.Base(
                        currentPair.toCurrency,
                        currentPair.fromCurrency,
                        rate
                    )
                }

            val favoritePairs = favoritePairsCacheDataSource.read()
            if (favoritePairs.isEmpty()) return DashboardResult.Empty
            return try {
                val updatedList = updatePairs(favoritePairs)



                DashboardResult.Success(listOfItem = updatedList)
            } catch (error: Exception) {
                DashboardResult.Error(handleError.handleError(error))
            }
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

private var count = 0.11
private var firstTime = true