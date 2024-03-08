package com.yrun.currencies

import android.content.Context
import androidx.room.Room
import com.yrun.data.core.ProvideResources
import com.yrun.data.dashboard.cache.PairDatabase
import com.yrun.data.load.cache.CurrencyDatabase
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync


interface Core {


    fun provideNavigation(): Navigation.Mutable

    fun provideResources(): ProvideResources

    fun provideRunAsync(): RunAsync

    fun provideCurrencyDataBase(): CurrencyDatabase

    fun providePairDatabase(): PairDatabase

    class Base(private val context: Context) : Core {

        private val navigation: Navigation.Mutable = Navigation.Base
        private val provideResources by lazy { BaseProvideResources(context) }
        private val runAsync by lazy { RunAsync.Base() }
        private val currencyDatabase by lazy {
            Room.databaseBuilder(
                context, CurrencyDatabase::class.java,
                "currencyDatabase"
            ).build()
        }
        private val pairDatabase by lazy {
            Room.databaseBuilder(
                context, PairDatabase::class.java,
                "pairDatabase"
            ).build()
        }

        override fun provideNavigation(): Navigation.Mutable = navigation

        override fun provideResources(): ProvideResources = provideResources

        override fun provideRunAsync(): RunAsync = runAsync

        override fun provideCurrencyDataBase(): CurrencyDatabase = currencyDatabase

        override fun providePairDatabase(): PairDatabase = pairDatabase


    }
}