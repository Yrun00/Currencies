package com.yrun.presentation.settings

import com.yrun.domain.premium.SaveResult
import com.yrun.domain.premium.SettingsInteractor
import com.yrun.presentation.FakeNavigation
import com.yrun.presentation.FakeRunAsync
import com.yrun.presentation.core.BundleWrapper
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.settings.adapter.ChoiceUi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SettingsViewModelTest {


    private lateinit var viewModel: SettingsViewModel
    private lateinit var interactor: FakeSettingsInteractor
    private lateinit var navigation: FakeNavigation
    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiObservable: FakeSettingsUiObservable

    @Before
    fun setup() {
        interactor = FakeSettingsInteractor()
        navigation = FakeNavigation()
        runAsync = FakeRunAsync()
        uiObservable = FakeSettingsUiObservable()
        viewModel = SettingsViewModel(
            interactor = interactor,
            navigation = navigation,
            runAsync = runAsync,
            observable = uiObservable
        )
    }

    @Test
    fun scenarioTest() {
        viewModel.init(BundleWrapper.Base(bundle = null))
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                SettingsUiState.Initial(
                    listOf(
                        ChoiceUi.Base(false, "USD"),
                        ChoiceUi.Base(false, "EUR"),
                        ChoiceUi.Base(false, "RUB")
                    )
                )
            )
        )
        viewModel.chooseFromCurrency(currency = "USD")
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                SettingsUiState.AvailableDestination(
                    fromCurrency =
                    listOf(
                        ChoiceUi.Base(true, "USD"),
                        ChoiceUi.Base(false, "EUR"),
                        ChoiceUi.Base(false, "RUB")
                    ),
                    toCurrency = listOf(
                        ChoiceUi.Base(false, "EUR"),
                        ChoiceUi.Base(false, "RUB")
                    )
                )
            )
        )
        viewModel.chooseToCurrency(fromCurrency = "USD", toCurrency = "EUR")
        runAsync.returnResult()
        uiObservable.check(
            listOf(
                SettingsUiState.SaveAvailable(
                    fromCurrency =
                    listOf(
                        ChoiceUi.Base(true, "USD"),
                        ChoiceUi.Base(false, "EUR"),
                        ChoiceUi.Base(false, "RUB")
                    ),
                    toCurrency = listOf(
                        ChoiceUi.Base(true, "EUR"),
                        ChoiceUi.Base(false, "RUB")
                    )
                )
            )
        )
        viewModel.saveSettings(fromCurrency = "USD", toCurrency = "EUR")
        interactor.checkSaveCalled()
        runAsync.returnResult()
        assertEquals(navigation.actual, DashboardScreen)
    }

    @Test
    fun lifeCycleTest() {
        val observer: UpdateUi<SettingsUiState> = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) = Unit
        }
        viewModel.startGettingUpdates(observer)
        uiObservable.checkObserver(observer)
        viewModel.stopGettingUpdates()
        uiObservable.checkEmpty(observer)
    }
}


class FakeSettingsUiObservable : SettingsUiObservable {

    private var cache: SettingsUiState = SettingsUiState.Empty
    private var observer: UpdateUi<SettingsUiState> = UpdateUi.Empty()
    private val actualList = mutableListOf<SettingsUiState>()
    private val expectedList = mutableListOf<SettingsUiState>()


    override fun updateObserver(observer: UpdateUi<SettingsUiState>) {
        this.observer = observer
    }

    override fun updateUi(uiState: SettingsUiState) {
        cache = uiState
        actualList.add(uiState)
    }

    fun check(list: List<SettingsUiState>) {
        expectedList.addAll(list)
        Assert.assertEquals(expectedList, actualList)
    }

    fun checkObserver(expected: UpdateUi<SettingsUiState>) {
        Assert.assertEquals(expected, observer)
    }

    fun checkEmpty(expected: UpdateUi<SettingsUiState>) {
        Assert.assertNotEquals(expected, observer)
    }
}

class FakeSettingsInteractor : SettingsInteractor {

    private var saveCalled = false

    override suspend fun allCurrencies(): List<String> {
        return listOf("USD", "EUR", "RUB")
    }

    override suspend fun availableDestinations(fromCurrency: String): List<String> {
        return allCurrencies().filterNot { it == fromCurrency }
    }

    override suspend fun save(toCurrency: String, fromCurrency: String): SaveResult {
        saveCalled = true
        return SaveResult.Success
    }

    fun checkSaveCalled() {
        assertTrue(saveCalled)
    }
}