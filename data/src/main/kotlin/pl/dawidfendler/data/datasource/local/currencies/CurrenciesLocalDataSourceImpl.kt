package pl.dawidfendler.data.datasource.local.currencies

import pl.dawidfendler.data.datasource.database.currencies.CurrenciesDao
import pl.dawidfendler.data.model.currency.local.currencies.ExchangeRateTableEntity
import javax.inject.Inject

class CurrenciesLocalDataSourceImpl @Inject constructor(
    private val currenciesDao: CurrenciesDao
) : CurrenciesLocalDataSource {
    override suspend fun insertAll(currencies: List<ExchangeRateTableEntity>) {
        currenciesDao.insertAll(currencies)
    }

    override suspend fun getAllCurrencies(): List<ExchangeRateTableEntity> {
        return currenciesDao.getAllCurrencies()
    }

    override suspend fun getCurrencyByCode(currencyCode: String): ExchangeRateTableEntity? {
        return currenciesDao.getCurrencyByCode(currencyCode)
    }

    override suspend fun deleteCurrencies() {
        currenciesDao.deleteCurrencies()
    }
}