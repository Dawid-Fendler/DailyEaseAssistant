package pl.dawidfendler.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.dawidfendler.data.database.currencies.CurrenciesDao
import pl.dawidfendler.data.database.user.UserDao
import pl.dawidfendler.data.model.currency.local.currencies.ExchangeRateTableEntity
import pl.dawidfendler.data.model.currency.local.user.UserEntity

@Database(
    entities = [
        ExchangeRateTableEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao
    abstract fun userDao(): UserDao
}
