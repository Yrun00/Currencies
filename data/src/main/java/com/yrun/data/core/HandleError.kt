package com.yrun.data.core

import java.net.UnknownHostException
import javax.inject.Inject

interface HandleError {

    fun handleError(error: Exception): String

    class Base @Inject constructor(private val provideResources: ProvideResources) : HandleError {
        override fun handleError(error: Exception): String {
            return if (error is UnknownHostException) {
                provideResources.noInternetConnectionMessage()
            } else
                provideResources.serviceUnavailableMessage()
        }
    }
}