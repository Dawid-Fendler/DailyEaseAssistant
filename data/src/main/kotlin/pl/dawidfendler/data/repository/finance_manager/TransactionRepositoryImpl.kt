package pl.dawidfendler.data.repository.finance_manager

import pl.dawidfendler.data.datasource.local.transaction.TransactionLocalDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.domain.model.transaction.Transaction
import pl.dawidfendler.domain.repository.finance_manager.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionLocalDataSource: TransactionLocalDataSource
) : TransactionRepository {

    override suspend fun insert(transaction: Transaction) {
        transactionLocalDataSource.insert(transaction.toEntity())
    }

    override suspend fun getTransaction(): List<Transaction> {
        return transactionLocalDataSource.getTransaction().map { it.toDomain() }
    }

    override suspend fun deleteTransaction() {
        transactionLocalDataSource.deleteTransaction()
    }
}
