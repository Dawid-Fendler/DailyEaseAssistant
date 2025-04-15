package pl.dawidfendler.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
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
import pl.dawidfendler.domain.use_case.currencies.GetSelectedCurrenciesUseCase
import pl.dawidfendler.domain.use_case.home.GetDisplayHomeUseCase
import pl.dawidfendler.domain.use_case.onboarding.GetOnboardingDisplayedUseCase
import pl.dawidfendler.domain.use_case.transaction.CreateTransactionUseCase
import pl.dawidfendler.domain.use_case.transaction.DeleteTransactionsUseCase
import pl.dawidfendler.domain.use_case.transaction.GetTransactionUseCase
import pl.dawidfendler.domain.use_case.user.CreateUserUseCase
import pl.dawidfendler.domain.use_case.user.DeleteUserUseCase
import pl.dawidfendler.domain.use_case.user.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user.GetUserCurrenciesUseCase
import pl.dawidfendler.domain.use_case.user.UpdateAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user.UpdateUserCurrenciesUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = LoginUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = LogoutUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGoogleLoginUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = GoogleLoginUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideRegistrationUseCase(
        authenticationRepository: AuthenticationRepository,
        dispatcherProvider: DispatcherProvider
    ) = RegistrationUseCase(
        authenticationRepository = authenticationRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetOnboardingDisplayedUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetOnboardingDisplayedUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetDisplayHomeUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetDisplayHomeUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteCurrenciesUseCase(
        currenciesRepository: CurrenciesRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteCurrenciesUseCase(
        currenciesRepository = currenciesRepository,
        dispatchers = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetCurrenciesByCodeUseCase(
        currenciesRepository: CurrenciesRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetCurrenciesByCodeUseCase(
        currenciesRepository = currenciesRepository,
        dispatchers = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetCurrenciesUseCase(
        currenciesRepository: CurrenciesRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetCurrenciesUseCase(
        currenciesRepository = currenciesRepository,
        dispatchers = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetAccountBalanceUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetAccountBalanceUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideUpdateAccountBalanceUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = UpdateAccountBalanceUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideCreateUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = CreateUserUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteUserUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideCreateTransactionUseCase(
        transactionRepository: TransactionRepository,
        dispatcherProvider: DispatcherProvider
    ) = CreateTransactionUseCase(
        transactionRepository = transactionRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteTransactionUseCase(
        transactionRepository: TransactionRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteTransactionsUseCase(
        transactionRepository = transactionRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetTransactionUseCase(
        transactionRepository: TransactionRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetTransactionUseCase(
        transactionRepository = transactionRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetUserCurrenciesUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetUserCurrenciesUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideUpdateUserCurrenciesUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = UpdateUserCurrenciesUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetSelectedCurrenciesUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetSelectedCurrenciesUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )
}
