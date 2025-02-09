package pl.dawidfendler.data.datasource.remote.currencies

import pl.dawidfendler.data.model.currency.remote.ExchangeRateTableResponse
import retrofit2.Response

interface CurrenciesRemoteDataSource {

    suspend fun getTableA(): Response<List<ExchangeRateTableResponse>>

    suspend fun getTableB(): Response<List<ExchangeRateTableResponse>>
}