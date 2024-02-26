package com.yrun.data.core

interface ProvideResources {

    fun noInternetConnectionMessage() : String

    fun serviceUnavailableMessage() : String
}