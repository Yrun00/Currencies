package com.yrun.currencies.modules

import com.yrun.currencies.ProvideInstance
import com.yrun.data.core.HandleError
import com.yrun.data.load.cache.CurrencyCacheDataSource
import com.yrun.data.load.cloud.CurrencyCloudDataSource
import com.yrun.domain.load.LoadCurrenciesRepository
import com.yrun.domain.load.LoadCurrenciesResult
import com.yrun.presentation.load.BaseLoadResultMapper
import com.yrun.presentation.load.LoadUiObservable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoadModule {
    @Binds
    @ViewModelScoped
    abstract fun bindObservable(
        observable: LoadUiObservable.Base
    ): LoadUiObservable

    @Binds
    abstract fun bindCloudDataSource(
        cloudDataSource: CurrencyCloudDataSource.Base
    ): CurrencyCloudDataSource

    @Binds
    abstract fun bindCacheDataSource(
        cacheDataSource: CurrencyCacheDataSource.Base
    ): CurrencyCacheDataSource.Mutable

    @Binds
    abstract fun bindMapper(mapper: BaseLoadResultMapper): LoadCurrenciesResult.Mapper

    companion object {
        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            cloudDataSource: CurrencyCloudDataSource,
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            handleError: HandleError.Base
        ): LoadCurrenciesRepository = provideInstance.provideLoadRepository(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            handleError = handleError
        )
    }
}