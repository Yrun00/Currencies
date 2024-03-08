package com.yrun.presentation.settings

interface CurrencyChoice {

    fun chooseFromCurrency(currency: String)

    fun chooseToCurrency(fromCurrency: String, toCurrency: String)

}
