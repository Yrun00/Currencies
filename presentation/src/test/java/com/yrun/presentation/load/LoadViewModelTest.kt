package com.yrun.presentation.load

import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.FakeRunAsync
import com.yrun.presentation.dashboard.DashboardScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {

    private lateinit var viewModel: LoadViewModel
    private lateinit var repository: FakeLoadCurrenciesRepository
    private lateinit var navigation: FakeNavigation
    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiObservable: FakeLoadUiObservable

    @Before
    fun setup() {
        repository = FakeLoadCurrenciesRepository()
        navigation = FakeNavigation()
        runAsync = FakeRunAsync()
        uiObservable = FakeLoadUiObservable()
        viewModel = LoadViewModel(
            repository = repository,
            navigation = navigation,
            runAsync = runAsync,
            uiObservable = uiObservable
        )
    }

    @Test
    fun testFirstRunErrorThenSuccess() {
        repository.error()
        viewModel.init(true)
        Assert.assertEquals(LoadUiState.Progress, uiObservable.actual)
        runAsync.returnResult()
        Assert.assertEquals(LoadUiState.Error("No internet"), uiObservable.actual)
        repository.success()
        viewModel.load()
        Assert.assertEquals(LoadUiState.Progress, uiObservable.actual)
        runAsync.returnResult()
        Assert.assertEquals(DashboardScreen, navigation.actual)
    }

    @Test
    fun testNotFirstRun() {
        viewModel.init(false)
        Assert.assertEquals(LoadUiState.Empty, uiObservable.actual)
    }
}