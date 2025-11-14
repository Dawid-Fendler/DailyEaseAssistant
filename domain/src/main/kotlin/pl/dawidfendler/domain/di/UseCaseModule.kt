package pl.dawidfendler.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.domain.repository.account.AccountRepository
import pl.dawidfendler.domain.repository.finance_manager.CurrenciesRepository
import pl.dawidfendler.domain.repository.finance_manager.TransactionRepository
import pl.dawidfendler.domain.use_case.account.DeleteAccountUseCase
import pl.dawidfendler.domain.use_case.account.DeleteAllAccountsUseCase
import pl.dawidfendler.domain.use_case.account.GetAccountByCurrencyUseCase
import pl.dawidfendler.domain.use_case.account.GetAccountsForUserUseCase
import pl.dawidfendler.domain.use_case.account.InsertOrUpdateAccountUseCase
import pl.dawidfendler.domain.use_case.account.InsertOrUpdateAccountsUseCase
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.domain.use_case.authentication.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication.LoginUseCase
import pl.dawidfendler.domain.use_case.authentication.LogoutUseCase
import pl.dawidfendler.domain.use_case.authentication.RegistrationUseCase
import pl.dawidfendler.domain.use_case.categories.DeleteAllUserCategoriesUseCase
import pl.dawidfendler.domain.use_case.categories.DeleteUserCategoryByNameUseCase
import pl.dawidfendler.domain.use_case.categories.GetExpenseCategoriesUseCase
import pl.dawidfendler.domain.use_case.categories.GetIncomeCategoriesUseCase
import pl.dawidfendler.domain.use_case.categories.InsertUserCategoryUseCase
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
import pl.dawidfendler.domain.use_case.user.GetUserUseCase
import pl.dawidfendler.domain.use_case.user.GetUserWithAccountsUseCase
import pl.dawidfendler.domain.use_case.user.UpdateUserUseCase

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
    fun provideGetSelectedCurrenciesUseCase(
        dataStore: DataStore,
        dispatcherProvider: DispatcherProvider
    ) = GetSelectedCurrenciesUseCase(
        dataStore = dataStore,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetUserUseCase(
        userRepository = userRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetUserWithAccountsUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetUserWithAccountsUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideUpdateUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ) = UpdateUserUseCase(
        userRepository = userRepository,
        dispatcher = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteAccountUseCase(
        accountRepository: AccountRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteAccountUseCase(
        accountRepository = accountRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteAllAccountsUseCase(
        accountRepository: AccountRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteAllAccountsUseCase(
        accountRepository = accountRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetAccountByCurrencyUseCase(
        accountRepository: AccountRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetAccountByCurrencyUseCase(
        accountRepository = accountRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetAccountsForUserUseCase(
        accountRepository: AccountRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetAccountsForUserUseCase(
        accountRepository = accountRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideInsertOrUpdateAccountsUseCase(
        accountRepository: AccountRepository,
        dispatcherProvider: DispatcherProvider
    ) = InsertOrUpdateAccountsUseCase(
        accountRepository = accountRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideInsertOrUpdateAccountUseCase(
        accountRepository: AccountRepository,
        dispatcherProvider: DispatcherProvider
    ) = InsertOrUpdateAccountUseCase(
        accountRepository = accountRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetIncomeCategoriesUseCase(
        categoriesRepository: CategoriesRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetIncomeCategoriesUseCase(
        getCategoriesRepository = categoriesRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideGetExpenseCategoriesUseCase(
        categoriesRepository: CategoriesRepository,
        dispatcherProvider: DispatcherProvider
    ) = GetExpenseCategoriesUseCase(
        getCategoriesRepository = categoriesRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteAllUserCategoriesUseCase(
        categoriesRepository: CategoriesRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteAllUserCategoriesUseCase(
        getCategoriesRepository = categoriesRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteUserCategoryByNameUseCase(
        categoriesRepository: CategoriesRepository,
        dispatcherProvider: DispatcherProvider
    ) = DeleteUserCategoryByNameUseCase(
        getCategoriesRepository = categoriesRepository,
        dispatcherProvider = dispatcherProvider
    )

    @Provides
    @ViewModelScoped
    fun provideInsertUserCategoryUseCase(
        categoriesRepository: CategoriesRepository,
        dispatcherProvider: DispatcherProvider
    ) = InsertUserCategoryUseCase(
        getCategoriesRepository = categoriesRepository,
        dispatcherProvider = dispatcherProvider
    )
}
