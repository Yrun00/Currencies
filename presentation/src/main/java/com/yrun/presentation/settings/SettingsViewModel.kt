package com.yrun.presentation.settings

import com.yrun.domain.settings.SettingsRepository
import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.main.BaseViewModel
import com.yrun.presentation.main.Clear
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import com.yrun.presentation.settings.adapter.ChoiceUi

class SettingsViewModel(
    runAsync: RunAsync,
    private val navigation: Navigation.Navigate,
    private val clear: Clear,
    private val repository: SettingsRepository,
    private val observable: UiObservable<SettingsUiState>,
) : BaseViewModel(runAsync), CurrencyChoice {

    fun init() {

        runAsync({
            repository.allCurrencies()
        }) { currencies ->
            val listCurrencies = currencies.map {
                ChoiceUi.Base(isSelected = false, currency = it)
            }
            observable.updateUi(SettingsUiState.Initial(listCurrencies))
        }
    }

    fun backToDashboard() {
        navigation.updateUi(DashboardScreen)
        clear.clear(SettingsViewModel::class.java)
    }

    override fun chooseFromCurrency(currency: String) {
        runAsync({
            val fromCurrencies = repository.allCurrencies().map {
                ChoiceUi.Base(isSelected = it == currency, currency = it)
            }
            val toCurrencies = repository.availableDestinations(currency).map {
                ChoiceUi.Base(isSelected = false, currency = it)
            }.let { it.ifEmpty { listOf(ChoiceUi.Empty) } }
            SettingsUiState.AvailableDestination(fromCurrencies, toCurrencies)
        }) {
            observable.updateUi(it)
        }
    }

    override fun chooseToCurrency(fromCurrency: String, toCurrency: String) {
        runAsync({
            val toCurrencies = repository.availableDestinations(fromCurrency)
            SettingsUiState.SaveAvailable(fromCurrency = repository.allCurrencies().map {
                ChoiceUi.Base(isSelected = it == fromCurrency, currency = it)
            }, toCurrency = toCurrencies.map {
                ChoiceUi.Base(isSelected = it == toCurrency, currency = it)
            })
        })
        { observable.updateUi(it) }
    }

    fun saveSettings(fromCurrency: String, toCurrency: String) {
        runAsync({ repository.save(fromCurrency = fromCurrency, toCurrency = toCurrency) }) {
            backToDashboard()
        }
    }

    fun startGettingUpdates(observer: UpdateUi<SettingsUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}