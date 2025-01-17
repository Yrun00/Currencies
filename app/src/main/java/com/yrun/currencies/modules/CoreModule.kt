package com.yrun.currencies.modules

import com.yrun.currencies.BasePremiumStorage
import com.yrun.currencies.BaseProvideResources
import com.yrun.currencies.ProvideInstance
import com.yrun.data.core.HandleError
import com.yrun.data.core.ProvideResources
import com.yrun.domain.premium.PremiumStorage
import com.yrun.presentation.main.Navigation
import com.yrun.presentation.main.RunAsync
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindProvideInstances(provideInstance: ProvideInstance.Base): ProvideInstance

    @Binds
    @Singleton
    abstract fun bindRunAsync(runAsync: RunAsync.Base): RunAsync

    @Binds
    @Singleton
    abstract fun bindProvideResources(provideResources: BaseProvideResources): ProvideResources

    @Binds
    @Singleton
    abstract fun bindHandleError(handleError: HandleError.Base): HandleError

    @Binds
    @Singleton
    abstract fun bindNavigation(navigation: Navigation.Base): Navigation.Mutable

    @Binds
    @Singleton
    abstract fun bindLocalStorage(premiumStorage: BasePremiumStorage): PremiumStorage.Mutable
}