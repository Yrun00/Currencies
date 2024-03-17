package com.yrun.presentation.dashboard

interface CurrencyPairUi {
    interface Concat {
        fun concat(fromCurrency: String, toCurrency: String): String
    }

    interface Derive {
        fun derive(pairUi: String): Pair<String, String>
    }

    interface Mutable : Concat, Derive

    class Base(private val separator: String = "/") : Mutable {
        override fun concat(fromCurrency: String, toCurrency: String): String {
            return toCurrency + separator + fromCurrency
        }

        override fun derive(pairUi: String): Pair<String, String> {
            val temp = pairUi.split(separator)
            return Pair(temp[0], temp[1])
        }
    }
}