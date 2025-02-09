package pl.dawidfendler.domain.repository

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.network.NetworkError

interface CurrenciesRepository {

    suspend fun getCurrenciesTableA(): DataResult<List<ExchangeRateTable>?, NetworkError>
    suspend fun getCurrenciesTableB(): DataResult<List<ExchangeRateTable>?, NetworkError>
    suspend fun deleteCurrencies()
    suspend fun getCurrenciesByCode(currencyCode: String): ExchangeRateTable?
}