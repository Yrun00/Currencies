package com.yrun.data.core

import java.net.UnknownHostException

interface HandleError {

    fun handleError(error: Exception): String

    class Base(private val provideResources: ProvideResources) : HandleError {
        override fun handleError(error: Exception): String {
            return if (error is UnknownHostException) {
                provideResources.noInternetConnectionMessage()
            } else
                provideResources.serviceUnavailableMessage()
        }
    }
}