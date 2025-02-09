package pl.dawidfendler.data.datasource.database.currencies

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.dawidfendler.data.model.currency.local.ExchangeRateTableEntity

@Database(
    entities = [ExchangeRateTableEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CurrenciesDatabase: RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao
}