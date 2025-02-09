package pl.dawidfendler.data.datasource.local

import pl.dawidfendler.data.model.currency.local.ExchangeRateTableEntity

interface CurrenciesLocalDataSource {
    suspend fun insertAll(currencies: List<ExchangeRateTableEntity>)
    suspend fun getAllCurrencies(): List<ExchangeRateTableEntity>
    suspend fun getCurrencyByCode(currencyCode: String): ExchangeRateTableEntity?
    suspend fun deleteCurrencies()
}