package com.yrun.currencies.modules

import android.content.Context
import androidx.room.Room
import com.yrun.data.load.cache.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CurrencyDatabase =
        Room.databaseBuilder(context, CurrencyDatabase::class.java, "currenciesDB").build()

    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase) = database.currencyDao()

    @Provides
    fun providesPairDao(database: CurrencyDatabase) = database.pairDao()

}