package pl.dawidfendler.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.use_case.authentication_use_case.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LogoutUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.RegistrationUseCase
import pl.dawidfendler.domain.use_case.onboarding_use_case.GetOnboardingDisplayedUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ) = LoginUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authenticationRepository: AuthenticationRepository
    ) = LogoutUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    @Singleton
    fun provideGoogleLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ) = GoogleLoginUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    @Singleton
    fun provideRegistrationUseCase(
        authenticationRepository: AuthenticationRepository
    ) = RegistrationUseCase(
        authenticationRepository = authenticationRepository
    )

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
