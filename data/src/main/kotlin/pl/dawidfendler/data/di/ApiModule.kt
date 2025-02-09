package pl.dawidfendler.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.service.currency.CurrenciesApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrenciesApi =
        retrofit.create(CurrenciesApi::class.java)
}