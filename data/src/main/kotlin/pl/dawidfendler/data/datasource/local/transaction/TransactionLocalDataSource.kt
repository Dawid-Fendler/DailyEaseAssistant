package pl.dawidfendler.data.datasource.local.transaction

import pl.dawidfendler.data.model.transaction.TransactionEntity

interface TransactionLocalDataSource {
    suspend fun insert(transaction: TransactionEntity)
    suspend fun getTransaction(): List<TransactionEntity>
    suspend fun deleteTransaction()
}
