package pl.dawidfendler.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.repository.AuthenticationRepositoryImpl
import pl.dawidfendler.data.repository.UserRepositoryImpl
import pl.dawidfendler.data.repository.account.AccountRepositoryImpl
import pl.dawidfendler.data.repository.finance_manager.CurrenciesRepositoryImpl
import pl.dawidfendler.data.repository.finance_manager.TransactionRepositoryImpl
import pl.dawidfendler.data.repository.finance_manager.CategoriesRepositoryImpl
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.domain.repository.account.AccountRepository
import pl.dawidfendler.domain.repository.finance_manager.CurrenciesRepository
import pl.dawidfendler.domain.repository.finance_manager.TransactionRepository
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    fun bindCurrenciesRepository(impl: CurrenciesRepositoryImpl): CurrenciesRepository

    @Binds
    @Singleton
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository
}
