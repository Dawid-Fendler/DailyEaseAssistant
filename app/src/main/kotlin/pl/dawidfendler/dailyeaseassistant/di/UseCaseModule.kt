package pl.dawidfendler.dailyeaseassistant.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.dailyeaseassistant.use_case.GetOnboardingDisplayedUseCase
import pl.dawidfendler.datastore.DataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetOnboardingDisplayedUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetOnboardingDisplayedUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )
}