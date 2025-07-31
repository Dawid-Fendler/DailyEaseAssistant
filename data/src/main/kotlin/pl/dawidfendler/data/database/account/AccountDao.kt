package pl.dawidfendler.data.database.account

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.dawidfendler.data.model.account.AccountEntity

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAccount(account: AccountEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAccounts(accounts: List<AccountEntity>)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE ownerUserId = :userId")
    suspend fun getAccountsForUser(userId: Long = 1L): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE ownerUserId = :userId AND currencyCode = :currencyCode")
    suspend fun getAccountByCurrency(userId: Long = 1L, currencyCode: String): AccountEntity?

    @Query("DELETE FROM accounts")
    suspend fun deleteAllAccounts()
}