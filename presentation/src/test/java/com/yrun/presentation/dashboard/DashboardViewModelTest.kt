package com.yrun.presentation.dashboard

import android.os.Parcelable
import com.yrun.domain.dashboard.DashboardItem
import com.yrun.domain.dashboard.DashboardRepository
import com.yrun.domain.dashboard.DashboardResult
import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.FakeRunAsync
import com.yrun.presentation.core.BundleWrapper
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.adapter.DashboardUi
import com.yrun.presentation.settings.SettingsScreen
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: FakeDashboardRepository
    private lateinit var navigation: FakeNavigation
    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiObservable: FakeDashboardUiObservable
    private lateinit var dashboardItemMapper: BaseDashboardItemMapper
    private lateinit var concat: CurrencyPairUi.Mutable
    private lateinit var handleDeath: FakeHandleDeath

    @Before
    fun setup() {
        repository = FakeDashboardRepository()
        navigation = FakeNavigation()
        runAsync = FakeRunAsync()
        uiObservable = FakeDashboardUiObservable()
        concat = CurrencyPairUi.Base(separator = "/")
        dashboardItemMapper = BaseDashboardItemMapper(concat = concat)
        handleDeath = FakeHandleDeath()
        viewModel = DashboardViewModel(
            repository = repository,
            navigation = navigation,
            runAsync = runAsync,
            observable = uiObservable,
            derive = CurrencyPairUi.Base(separator = "/"),
            mapper = BaseDashboardResultMapper(uiObservable, dashboardItemMapper),
            handleDeath = handleDeath
        )
    }


    @Test
    fun testProcessRecreate() {
        val wrapper = FakeWrapper()
        viewModel.init(wrapper)
        uiObservable.check(
            listOf(
                DashboardUiState.Progress
            )
        )
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Empty
            )
        )
        wrapper.notEmpty()
        handleDeath.checkResetNotCalled()
        setup()
        viewModel.init(wrapper)
        uiObservable.check(
            listOf(
                DashboardUiState.Progress
            )
        )
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Empty
            )
        )
        handleDeath.checkResetCalled()
    }

    @Test
    fun testInit() {
        val wrapper = FakeWrapper()
        viewModel.init(wrapper)
        uiObservable.check(
            listOf(
                DashboardUiState.Progress
            )
        )
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Empty
            )
        )
        handleDeath.checkFirstTimeCalled()

        wrapper.notEmpty()
        viewModel.init(wrapper)
        handleDeath.checkResetNotCalled()
        uiObservable.check(
            listOf(
                DashboardUiState.Progress,
                DashboardUiState.Empty
            )
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
    }
}

class FakeHandleDeath : HandleDeath {

    private var deathHappened = true
    private var resetCalled = false
    private var firstTimeCalled = false

    override fun firstTime() {
        deathHappened = false
        firstTimeCalled = true
    }

    fun checkResetCalled() {
        assertTrue(resetCalled)
    }

    fun checkResetNotCalled() {
        assertFalse(resetCalled)
    }

    fun checkFirstTimeCalled() {
        assertTrue(firstTimeCalled)
    }

    fun checkFirstTimeNotCalled() {
        assertFalse(firstTimeCalled)
    }

    override fun reset() {
        deathHappened = false
        resetCalled = true
    }

    override fun deathHappened(): Boolean {
        return deathHappened
    }

}

class FakeWrapper : BundleWrapper.Mutable {

    private var isEmpty: Boolean = true

    override fun isEmpty(): Boolean {
        return isEmpty
    }

    fun notEmpty() {
        isEmpty = false
    }

    override fun save(choice: String, key: String) = Unit

    override fun save(parcelable: Parcelable?, key: String) = Unit

    override fun restore(key: String): String = "Unit"

    override fun restoreTo(): String = "Unit"

    override fun restoreFrom(): String = "Unit"


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

    private var actual: DashboardResult = DashboardResult.Empty

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