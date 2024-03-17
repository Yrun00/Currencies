package com.yrun.currencies

import android.content.Context
import com.yrun.data.core.ProvideResources

class BaseProvideResources(private val context: Context): ProvideResources {

    override fun noInternetConnectionMessage(): String {
        return context.resources.getString(R.string.no_internet_connection)
    }

    override fun serviceUnavailableMessage(): String {
        return context.resources.getString(R.string.service_unavailable)
    }

}