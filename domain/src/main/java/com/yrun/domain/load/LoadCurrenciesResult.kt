package com.yrun.domain.load

interface LoadCurrenciesResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapError(message: String)

        fun mapSuccess()
    }

    object Success : LoadCurrenciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess()
        }
    }

    data class Error(private val message: String) : LoadCurrenciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }
}