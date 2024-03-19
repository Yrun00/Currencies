package com.yrun.currencies.modules

import com.yrun.currencies.BasePremiumStorage
import com.yrun.currencies.ProvideInstance
import com.yrun.domain.premium.PremiumStorage
import com.yrun.domain.premium.SaveResult
import com.yrun.domain.premium.SettingsInteractor
import com.yrun.domain.settings.SettingsRepository
import com.yrun.presentation.premium.BasePremiumResultMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PremiumModule {

    @Binds
    abstract fun bindSavePremiumStorage(
        premiumStorage: BasePremiumStorage
    ): PremiumStorage.Save

    @Binds
    abstract fun bindSettingsInteractor(
        settingsInteractor: SettingsInteractor.Base
    ): SettingsInteractor

    @Binds
    abstract fun bindPremiumResultMapper(
        premiumResultMapper: BasePremiumResultMapper
    ): SaveResult.Mapper

    companion object {
        @Provides
        fun provideInteractor(
            provideInstance: ProvideInstance,
            repository: SettingsRepository,
            premiumStorage: BasePremiumStorage
        ) = SettingsInteractor.Base(
            repository = repository,
            premiumStorage = premiumStorage,
            maxFreePairsCount = provideInstance.provideMaxFreeSavedPairsCount()
        )
    }
}
