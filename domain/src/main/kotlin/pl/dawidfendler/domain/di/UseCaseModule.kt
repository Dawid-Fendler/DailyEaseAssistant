package pl.dawidfendler.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.domain.use_case.authentication_use_case.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LogoutUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.RegistrationUseCase
import pl.dawidfendler.domain.use_case.currencies_use_case.DeleteCurrenciesUseCase
import pl.dawidfendler.domain.use_case.currencies_use_case.GetCurrenciesByCodeUseCase
import pl.dawidfendler.domain.use_case.currencies_use_case.GetCurrenciesUseCase
import pl.dawidfendler.domain.use_case.home_use_case.GetDisplayHomeUseCase
import pl.dawidfendler.domain.use_case.onboarding_use_case.GetOnboardingDisplayedUseCase
import pl.dawidfendler.domain.use_case.user_use_case.CreateUserUseCase
import pl.dawidfendler.domain.use_case.user_use_case.DeleteUserUseCase
import pl.dawidfendler.domain.use_case.user_use_case.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user_use_case.UpdateAccountBalanceUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ) = LoginUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    fun provideLogoutUseCase(
        authenticationRepository: AuthenticationRepository
    ) = LogoutUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    fun provideGoogleLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ) = GoogleLoginUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    fun provideRegistrationUseCase(
        authenticationRepository: AuthenticationRepository
    ) = RegistrationUseCase(
        authenticationRepository = authenticationRepository
    )

    @Provides
    fun provideGetOnboardingDisplayedUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetOnboardingDisplayedUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    fun provideGetDisplayHomeUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetDisplayHomeUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    fun provideDeleteCurrenciesUseCase(
        currenciesRepository: CurrenciesRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteCurrenciesUseCase(
        currenciesRepository = currenciesRepository,
        dispatchers = dispatcherProvider
    )

    @Provides
    fun provideGetCurrenciesByCodeUseCase(
        currenciesRepository: CurrenciesRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetCurrenciesByCodeUseCase(
        currenciesRepository = currenciesRepository,
        dispatchers = dispatcherProvider
    )

    @Provides
    fun provideGetCurrenciesUseCase(
        currenciesRepository: CurrenciesRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetCurrenciesUseCase(
        currenciesRepository = currenciesRepository,
        dispatchers = dispatcherProvider
    )

    @Provides
    fun provideGetAccountBalanceUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetAccountBalanceUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    fun provideUpdateAccountBalanceUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = UpdateAccountBalanceUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    fun provideCreateUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = CreateUserUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    fun provideDeleteUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteUserUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )
}
