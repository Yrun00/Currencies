package com.yrun.presentation.dashboard

import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.FakeClear
import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.FakeRunAsync
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.adapter.DashboardUi
import com.yrun.presentation.settings.SettingsScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: FakeDashboardRepository
    private lateinit var clear: FakeClear
    private lateinit var navigation: FakeNavigation
    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiObservable: FakeDashboardUiObservable
    private lateinit var derive: CurrencyPairUi.Derive
    private lateinit var mapper: BaseDashboardResultMapper
    private lateinit var dashboardItemMapper: BaseDashboardItemMapper
    private lateinit var concat: CurrencyPairUi.Concat

    @Before
    fun setup() {
        repository = FakeDashboardRepository()
        clear = FakeClear()
        navigation = FakeNavigation()
        runAsync = FakeRunAsync()
        uiObservable = FakeDashboardUiObservable()
        concat = CurrencyPairUi.Base()
        dashboardItemMapper = BaseDashboardItemMapper(concat)
        viewModel = DashboardViewModel(
            repository = repository,
            clear = clear,
            navigation = navigation,
            runAsync = runAsync,
            observable = uiObservable,
            derive = CurrencyPairUi.Base(),
            mapper = BaseDashboardResultMapper(uiObservable, dashboardItemMapper)

        )
    }

    @Test
    fun testErrorThenSuccessThenDeletePair() {
        repository.expectResult(DashboardResult.Error("No internet"))
        viewModel.load()
        uiObservable.check(
            listOf(DashboardUiState.Progress)
        )
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Error("No internet")
            )
        )
        repository.expectResult(
            DashboardResult.Success(
                listOfItem = listOf(
                    DashboardItem.Base(
                        "USD",
                        "EUR",
                        1.1
                    )
                )
            )
        )
        viewModel.retry()
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Error("No internet"),
                DashboardUiState.Progress,
                DashboardUiState.Base(pairs = listOf(DashboardUi.Success("EUR/USD", "1,10")))
            )
        )
        viewModel.deletePair("EUR/USD")
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Error("No internet"),
                DashboardUiState.Progress,
                DashboardUiState.Base(pairs = listOf(DashboardUi.Success("EUR/USD", "1,10"))),
                DashboardUiState.Empty

            )
        )

    }

    @Test
    fun goToSettings() {
        viewModel.goToSettings()
        Assert.assertEquals(SettingsScreen, navigation.actual)
        clear.checkCalled(DashboardViewModel::class.java)
    }
}

class FakeDashboardUiObservable : DashboardUiObservable {

    private var cache: DashboardUiState = DashboardUiState.Empty
    private var observer: UpdateUi<DashboardUiState> = UpdateUi.Empty()
    private val actualList = mutableListOf<DashboardUiState>()


    override fun updateObserver(observer: UpdateUi<DashboardUiState>) {
        this.observer = observer
    }

    override fun updateUi(uiState: DashboardUiState) {
        cache = uiState
        actualList.add(uiState)
    }

    fun check(list: List<DashboardUiState>) {
        Assert.assertEquals(list, actualList)
    }
}

class FakeDashboardRepository : DashboardRepository {

    private lateinit var actual: DashboardResult

    override suspend fun dashboard(): DashboardResult {
        return actual
    }

    override suspend fun deletePair(from: String, to: String): DashboardResult {
        actual = DashboardResult.Empty
        return actual
    }

    fun expectResult(result: DashboardResult) {
        actual = result
    }
}
