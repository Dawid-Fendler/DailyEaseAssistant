package pl.dawidfendler.data.repository

import pl.dawidfendler.data.datasource.local.currencies.CurrenciesLocalDataSource
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.data.util.Constants.TABLE_A_NAME
import pl.dawidfendler.data.util.Constants.TABLE_B_NAME
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
        val lastUpdatedDate = currenciesFromDb.firstOrNull {
            it.tableName == TABLE_A_NAME
        }?.lastUpdate?.let {
            dateTimeUtils.isToday(
                dateTime = dateTimeUtils.convertStringToOffsetDateTimeIsoFormat(it)
            )
        }
        return if (currenciesFromDb.isNotEmpty() || lastUpdatedDate == true) {
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
                    deleteCurrencies()
                    getTableA.data?.map { it.toEntity(dateTimeUtils, TABLE_A_NAME) }
                        ?.let { currenciesLocalDataSource.insertAll(it) }
                }

                else -> Unit
            }
            getTableA
        }
    }

    override suspend fun getCurrenciesTableB(): DataResult<List<ExchangeRateTable>?, NetworkError> {
        val currenciesFromDb = currenciesLocalDataSource.getAllCurrencies()
        val lastUpdatedDate = currenciesFromDb.firstOrNull {
            it.tableName == TABLE_B_NAME
        }?.lastUpdate?.let {
            dateTimeUtils.isToday(
                dateTime = dateTimeUtils.convertStringToOffsetDateTimeIsoFormat(it)
            )
        }
        if (lastUpdatedDate == true) {
            return DataResult.Success(emptyList())
        }

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
                getTableB.data?.map { it.toEntity(dateTimeUtils, TABLE_B_NAME) }
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
