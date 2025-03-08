package pl.dawidfendler.domain.repository

import pl.dawidfendler.domain.model.transaction.Transaction

interface TransactionRepository {
    suspend fun insert(transaction: Transaction)
    suspend fun getTransaction(): List<Transaction>
    suspend fun deleteTransaction()
}
