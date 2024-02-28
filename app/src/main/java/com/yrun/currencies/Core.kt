package com.yrun.currencies

import android.content.Context
import androidx.room.Room
import com.yrun.data.cache.CurrencyDatabase
import com.yrun.data.core.ProvideResources
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync


interface Core {


    fun provideNavigation(): Navigation

    fun provideResources(): ProvideResources

    fun provideRunAsync(): RunAsync

    fun provideDataBase(): CurrencyDatabase

    class Base(private val context: Context) : Core {

        private val navigation: Navigation by lazy { Navigation.Base() }
        private val provideResources by lazy { BaseProvideResources(context) }
        private val runAsync by lazy { RunAsync.Base() }
        private val database by lazy {
            Room.databaseBuilder(
                context, CurrencyDatabase::class.java,
                "currencyDatabase"
            ).build()
        }

        override fun provideNavigation(): Navigation = navigation

        override fun provideResources(): ProvideResources = provideResources

        override fun provideRunAsync(): RunAsync = runAsync

        override fun provideDataBase(): CurrencyDatabase = database


    }
}