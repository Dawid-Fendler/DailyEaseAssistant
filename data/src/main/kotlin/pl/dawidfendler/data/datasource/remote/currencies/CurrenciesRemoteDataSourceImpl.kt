package pl.dawidfendler.data.datasource.remote.currencies

import pl.dawidfendler.data.model.currency.remote.ExchangeRateTableResponse
import pl.dawidfendler.data.service.currency.CurrenciesApi
import retrofit2.Response
import javax.inject.Inject

class CurrenciesRemoteDataSourceImpl @Inject constructor(
    private val currenciesApi: CurrenciesApi
) : CurrenciesRemoteDataSource {

    override suspend fun getTableA(): Response<List<ExchangeRateTableResponse>> {
        return currenciesApi.getTableA()
    }

    override suspend fun getTableB(): Response<List<ExchangeRateTableResponse>> {
        return currenciesApi.getTableB()
    }
}