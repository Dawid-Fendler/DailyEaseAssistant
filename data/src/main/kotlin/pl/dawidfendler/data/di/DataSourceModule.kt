package pl.dawidfendler.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.datasource.database.currencies.CurrenciesDatabase
import pl.dawidfendler.data.datasource.database.user.UserDatabase
import pl.dawidfendler.data.datasource.local.currencies.CurrenciesLocalDataSource
import pl.dawidfendler.data.datasource.local.currencies.CurrenciesLocalDataSourceImpl
import pl.dawidfendler.data.datasource.local.user.UserLocalDataSource
import pl.dawidfendler.data.datasource.local.user.UserLocalDataSourceImpl
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSource
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSourceImpl
import pl.dawidfendler.data.service.currency.CurrenciesApi

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideCurrenciesRemoteDataSource(api: CurrenciesApi): CurrenciesRemoteDataSource =
        CurrenciesRemoteDataSourceImpl(api)

    @Provides
    fun provideCurrenciesLocalDataSource(db: CurrenciesDatabase): CurrenciesLocalDataSource =
        CurrenciesLocalDataSourceImpl(db.currenciesDao())

    @Provides
    fun provideUserLocalDataSource(db: UserDatabase): UserLocalDataSource =
        UserLocalDataSourceImpl(db.userDao())
}