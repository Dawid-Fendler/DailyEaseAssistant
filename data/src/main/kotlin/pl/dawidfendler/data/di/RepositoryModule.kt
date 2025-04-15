package pl.dawidfendler.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.repository.AuthenticationRepositoryImpl
import pl.dawidfendler.data.repository.CurrenciesRepositoryImpl
import pl.dawidfendler.data.repository.TransactionRepositoryImpl
import pl.dawidfendler.data.repository.UserRepositoryImpl
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.domain.repository.TransactionRepository
import pl.dawidfendler.domain.repository.UserRepository
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
}
