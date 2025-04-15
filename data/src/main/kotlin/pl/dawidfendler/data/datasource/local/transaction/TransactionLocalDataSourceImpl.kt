package pl.dawidfendler.data.datasource.local.transaction

import pl.dawidfendler.data.database.finance_manger.transaction.TransactionDao
import pl.dawidfendler.data.model.transaction.TransactionEntity

class TransactionLocalDataSourceImpl(
    private val transactionDao: TransactionDao
) : TransactionLocalDataSource {

    override suspend fun insert(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    override suspend fun getTransaction(): List<TransactionEntity> {
        return transactionDao.getTransaction()
    }

    override suspend fun deleteTransaction() {
        transactionDao.deleteTransaction()
    }
}
