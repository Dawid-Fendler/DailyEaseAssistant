package pl.dawidfendler.data.repository

import android.util.Log
import pl.dawidfendler.data.datasource.local.CurrenciesLocalDataSource
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.data.util.safeCall
import pl.dawidfendler.date.DateTimeUtils
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.map
import pl.dawidfendler.util.network.NetworkError
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val currenciesRemoteDataSource: CurrenciesRemoteDataSource,
    private val currenciesLocalDataSource: CurrenciesLocalDataSource,
    private val dateTimeUtils: DateTimeUtils
) : CurrenciesRepository {

    override suspend fun getCurrenciesTableA(): DataResult<List<ExchangeRateTable>?, NetworkError> {
       val currenciesFromDb = currenciesLocalDataSource.getAllCurrencies()
        return if (currenciesFromDb.isNotEmpty()) {
            DataResult.Success(currenciesFromDb.map { it.toDomain() })
        } else {
            val getTableA = safeCall(
                responseName = "CurrenciesApi GetTableA"
            ) {
                currenciesRemoteDataSource.getTableA()
            }.map { response ->
                response?.flatMap {
                    it.rates.map { rates ->
                        rates.toDomain()
                    }
                }
            }

            when (getTableA) {
                is DataResult.Success -> {
                    getTableA.data?.map { it.toEntity(dateTimeUtils) }
                        ?.let { currenciesLocalDataSource.insertAll(it) }
                }

                else -> Unit
            }
            getTableA
        }
    }

    override suspend fun getCurrenciesTableB(): DataResult<List<ExchangeRateTable>?, NetworkError> {
            val getTableB = safeCall(
                responseName = "CurrenciesApi GetTableB"
            ) {
                currenciesRemoteDataSource.getTableB()
            }.map { response ->
                response?.flatMap {
                    it.rates.map { rates ->
                        rates.toDomain()
                    }
                }
            }

            when (getTableB) {
                is DataResult.Success -> {
                    getTableB.data?.map { it.toEntity(dateTimeUtils) }
                        ?.let { currenciesLocalDataSource.insertAll(it) }
                }

                else -> Unit
            }
            return getTableB
    }

    override suspend fun deleteCurrencies() {
        currenciesLocalDataSource.deleteCurrencies()
    }

    override suspend fun getCurrenciesByCode(currencyCode: String): ExchangeRateTable? {
        return currenciesLocalDataSource.getCurrencyByCode(currencyCode)?.toDomain()
    }
}