package com.yrun.domain.dashboard

interface DashboardItem {

    interface Mapper<T : Any> {

        fun map(fromCurrency: String, toCurrency: String, rate: Double): T
    }

    fun <T : Any> map(mapper: Mapper<T>): T

    data class Base(
        private val toCurrency: String,
        private val fromCurrency: String,
        private val rate: Double
    ) : DashboardItem {
        override fun <T : Any> map(mapper: Mapper<T>): T =
            mapper.map(toCurrency, fromCurrency, rate)
    }
}