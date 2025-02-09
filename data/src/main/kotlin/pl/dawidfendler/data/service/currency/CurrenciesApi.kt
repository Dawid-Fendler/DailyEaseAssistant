package pl.dawidfendler.data.service.currency

import pl.dawidfendler.data.model.currency.remote.ExchangeRateTableResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrenciesApi {

    @GET("exchangerates/tables/A/")
    suspend fun getTableA(): Response<List<ExchangeRateTableResponse>>

    @GET("exchangerates/tables/B/")
    suspend fun getTableB(): Response<List<ExchangeRateTableResponse>>
}