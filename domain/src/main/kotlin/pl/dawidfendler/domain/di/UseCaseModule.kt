package pl.dawidfendler.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.domain.repository.TransactionRepository
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
import pl.dawidfendler.domain.use_case.transaction_use_case.CreateTransactionUseCase
import pl.dawidfendler.domain.use_case.transaction_use_case.DeleteTransactionsUseCase
import pl.dawidfendler.domain.use_case.transaction_use_case.GetTransactionUseCase
import pl.dawidfendler.domain.use_case.user_use_case.CreateUserUseCase
import pl.dawidfendler.domain.use_case.user_use_case.DeleteUserUseCase
import pl.dawidfendler.domain.use_case.user_use_case.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user_use_case.UpdateAccountBalanceUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = LoginUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    fun provideLogoutUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = LogoutUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    fun provideGoogleLoginUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = GoogleLoginUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    fun provideRegistrationUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = RegistrationUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
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

    @Provides
    fun provideCreateTransactionUseCase(
        transactionRepository: TransactionRepository,
        dispatcherProvider: DispatcherProvider
    ) = CreateTransactionUseCase(
        transactionRepository = transactionRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    fun provideDeleteTransactionUseCase(
        transactionRepository: TransactionRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteTransactionsUseCase(
        transactionRepository = transactionRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    fun provideGetTransactionUseCase(
        transactionRepository: TransactionRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetTransactionUseCase(
        transactionRepository = transactionRepository,
        dispatcher = dispatcherProvider
    )
}
