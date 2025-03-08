package pl.dawidfendler.data.database.finance_manger.currencies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.dawidfendler.data.model.currency.local.currencies.ExchangeRateTableEntity

@Dao
interface CurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<ExchangeRateTableEntity>)

    @Query("SELECT * FROM currencies ORDER BY name ASC")
    suspend fun getAllCurrencies(): List<ExchangeRateTableEntity>

    @Query("SELECT * FROM currencies WHERE code = :currencyCode LIMIT 1")
    suspend fun getCurrencyByCode(currencyCode: String): ExchangeRateTableEntity?

    @Query("DELETE FROM currencies")
    suspend fun deleteCurrencies()
}
