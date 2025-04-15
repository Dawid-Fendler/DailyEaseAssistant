package pl.dawidfendler.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.database.UserDatabase
import pl.dawidfendler.data.datasource.local.currencies.CurrenciesLocalDataSource
import pl.dawidfendler.data.datasource.local.currencies.CurrenciesLocalDataSourceImpl
import pl.dawidfendler.data.datasource.local.transaction.TransactionLocalDataSource
import pl.dawidfendler.data.datasource.local.transaction.TransactionLocalDataSourceImpl
import pl.dawidfendler.data.datasource.local.user.UserLocalDataSource
import pl.dawidfendler.data.datasource.local.user.UserLocalDataSourceImpl
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSource
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSourceImpl
import pl.dawidfendler.data.service.currency.CurrenciesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideCurrenciesRemoteDataSource(api: CurrenciesApi): CurrenciesRemoteDataSource =
        CurrenciesRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideCurrenciesLocalDataSource(db: UserDatabase): CurrenciesLocalDataSource =
        CurrenciesLocalDataSourceImpl(db.currenciesDao())

    @Provides
    @Singleton
    fun provideUserLocalDataSource(db: UserDatabase): UserLocalDataSource =
        UserLocalDataSourceImpl(db.userDao())

    @Provides
    @Singleton
    fun provideTransactionLocalDataSource(db: UserDatabase): TransactionLocalDataSource =
        TransactionLocalDataSourceImpl(db.transactionDao())
}
