package com.yrun.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyPairUiTest {

    private lateinit var currencyPairUi: CurrencyPairUi.Base

    @Before
    fun setup() {
        currencyPairUi = CurrencyPairUi.Base(separator = "/")
    }

    @Test
    fun concat() {
        assertEquals(currencyPairUi.concat(fromCurrency = "USD", toCurrency = "EUR"), "EUR/USD")
    }

    @Test
    fun derive() {
        assertEquals(currencyPairUi.derive("EUR/USD"), Pair("EUR", "USD"))

    }
}