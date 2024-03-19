package com.yrun.presentation.premium

import com.yrun.domain.premium.PremiumStorage
import com.yrun.domain.premium.SaveResult
import com.yrun.domain.premium.SettingsInteractor
import com.yrun.presentation.main.BaseViewModel
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import com.yrun.presentation.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val premiumStorage: PremiumStorage.Save,
    private val navigation: Navigation.Mutable,
    private val interactor: SettingsInteractor,
    private val premiumMapper: SaveResult.Mapper = BasePremiumResultMapper(navigation),
    runAsync: RunAsync
) : BaseViewModel(runAsync) {

    fun backToSettings() {
        navigation.updateUi(Screen.Pop)
    }

    fun buyPremiumAndGoToDashboard(pair: Pair<String, String>) {
        runAsync({
            premiumStorage.savePremium()
            interactor.save(
                fromCurrency = pair.first, toCurrency = pair.second
            )
        }) { premiumResult ->
            premiumResult.map(premiumMapper)
        }
    }
}
