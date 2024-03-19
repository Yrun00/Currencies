package com.yrun.currencies

import android.content.Context
import com.yrun.domain.premium.PremiumStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BasePremiumStorage
@Inject constructor(
    @ApplicationContext context: Context
) : PremiumStorage.Mutable {

    private val sharedPreferences =
        context.getSharedPreferences("premium_storage", Context.MODE_PRIVATE)


    override fun havePremium(): Boolean {
        return sharedPreferences.getBoolean(KEY, false)
    }

    override fun savePremium() {
        sharedPreferences.edit().putBoolean(KEY, true).apply()
    }

    companion object {
        private const val KEY = "PREMIUM_KEY"
    }
}