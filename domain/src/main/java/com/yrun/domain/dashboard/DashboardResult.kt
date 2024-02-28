package com.yrun.domain.dashboard

interface DashboardResult {

    interface Mapper {

        fun mapSuccess(listOfItem: List<DashboardItem>)

        fun mapError(message: String)

        fun mapEmpty()
    }

    fun map(mapper: Mapper)

    object Empty : DashboardResult {
        override fun map(mapper: Mapper) {
            mapper.mapEmpty()
        }
    }

    data class Error(private val message: String) : DashboardResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }

    data class Success(private val listOfItem: List<DashboardItem>) : DashboardResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(listOfItem)
        }
    }
}