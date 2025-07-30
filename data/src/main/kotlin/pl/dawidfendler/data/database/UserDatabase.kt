package pl.dawidfendler.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.dawidfendler.data.database.account.AccountDao
import pl.dawidfendler.data.database.finance_manger.currencies.CurrenciesDao
import pl.dawidfendler.data.database.finance_manger.transaction.TransactionDao
import pl.dawidfendler.data.database.users.UserDao
import pl.dawidfendler.data.model.account.AccountEntity
import pl.dawidfendler.data.model.currency.local.currencies.ExchangeRateTableEntity
import pl.dawidfendler.data.model.transaction.TransactionEntity
import pl.dawidfendler.data.model.user.UserEntity

@Database(
    entities = [
        ExchangeRateTableEntity::class,
        UserEntity::class,
        TransactionEntity::class,
        AccountEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao
    abstract fun usersDao(): UserDao
    abstract fun transactionsDao(): TransactionDao
    abstract fun accountsDao(): AccountDao
}
