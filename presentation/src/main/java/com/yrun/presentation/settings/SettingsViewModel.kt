package com.yrun.presentation.settings

import com.yrun.domain.premium.SaveResult
import com.yrun.domain.premium.SettingsInteractor
import com.yrun.presentation.core.BundleWrapper
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.DashboardScreen
import com.yrun.presentation.main.BaseViewModel
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import com.yrun.presentation.main.log
import com.yrun.presentation.premium.BasePremiumResultMapper
import com.yrun.presentation.settings.adapter.ChoiceUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    runAsync: RunAsync,
    private val navigation: Navigation.Mutable,
    private val interactor: SettingsInteractor,
    private val observable: SettingsUiObservable,
    private val premiumMapper: SaveResult.Mapper = BasePremiumResultMapper(navigation)
) : BaseViewModel(runAsync), CurrencyChoice {

    init {
        log("SettingsViewModel created")
    }

    override fun onCleared() {
        log("SettingsViewModel destroyed")
        super.onCleared()
    }

    fun init(bundleWrapper: BundleWrapper.Mutable) {
        if (bundleWrapper.isEmpty()) {
            runAsync({
                interactor.allCurrencies()
            }) { currencies ->
                val listCurrencies = currencies.map {
                    ChoiceUi.Base(isSelected = false, currency = it)
                }
                observable.updateUi(SettingsUiState.Initial(listCurrencies))
            }
        } else {
            val chosenFrom = bundleWrapper.restoreFrom()
            val chosenTo = bundleWrapper.restoreTo()
            val isChosenFrom = chosenFrom.isNotEmpty()
            val isChosenTo = chosenTo.isNotEmpty()

            if (isChosenTo && isChosenFrom) chooseToCurrency(
                fromCurrency = chosenFrom,
                toCurrency = chosenTo
            )

            if (isChosenFrom && !isChosenTo) chooseFromCurrency(currency = chosenFrom)

        }
    }


    fun backToDashboard() {
        navigation.updateUi(DashboardScreen)
    }

    override fun chooseFromCurrency(currency: String) {
        runAsync({
            val fromCurrencies = interactor.allCurrencies().map {
                ChoiceUi.Base(isSelected = it == currency, currency = it)
            }
            val toCurrencies = interactor.availableDestinations(currency).map {
                ChoiceUi.Base(isSelected = false, currency = it)
            }.let { it.ifEmpty { listOf(ChoiceUi.Empty) } }
            SettingsUiState.AvailableDestination(fromCurrencies, toCurrencies)
        }) {
            observable.updateUi(it)
        }
    }

    override fun chooseToCurrency(fromCurrency: String, toCurrency: String) {
        runAsync({
            val toCurrencies = interactor.availableDestinations(fromCurrency)
            SettingsUiState.SaveAvailable(fromCurrency = interactor.allCurrencies().map {
                ChoiceUi.Base(isSelected = it == fromCurrency, currency = it)
            }, toCurrency = toCurrencies.map {
                ChoiceUi.Base(isSelected = it == toCurrency, currency = it)
            })
        })
        { observable.updateUi(it) }
    }

    fun saveSettings(fromCurrency: String, toCurrency: String) {
        runAsync({
            interactor.save(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency
            )
        }) { premiumResult ->
            premiumResult.map(premiumMapper)
        }
    }

    fun startGettingUpdates(observer: UpdateUi<SettingsUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}