package pl.dawidfendler.data.database.finance_manger.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.dawidfendler.data.model.transaction.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM `transactions`")
    suspend fun getTransaction(): List<TransactionEntity>

    @Query("DELETE FROM `transactions`")
    suspend fun deleteTransaction()
}
