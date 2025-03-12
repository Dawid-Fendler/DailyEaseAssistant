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
import pl.dawidfendler.domain.use_case.authentication.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication.LoginUseCase
import pl.dawidfendler.domain.use_case.authentication.LogoutUseCase
import pl.dawidfendler.domain.use_case.authentication.RegistrationUseCase
import pl.dawidfendler.domain.use_case.currencies.DeleteCurrenciesUseCase
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesByCodeUseCase
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.domain.use_case.home.GetDisplayHomeUseCase
import pl.dawidfendler.domain.use_case.onboarding.GetOnboardingDisplayedUseCase
import pl.dawidfendler.domain.use_case.transaction.CreateTransactionUseCase
import pl.dawidfendler.domain.use_case.transaction.DeleteTransactionsUseCase
import pl.dawidfendler.domain.use_case.transaction.GetTransactionUseCase
import pl.dawidfendler.domain.use_case.user.CreateUserUseCase
import pl.dawidfendler.domain.use_case.user.DeleteUserUseCase
import pl.dawidfendler.domain.use_case.user.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user.UpdateAccountBalanceUseCase

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
