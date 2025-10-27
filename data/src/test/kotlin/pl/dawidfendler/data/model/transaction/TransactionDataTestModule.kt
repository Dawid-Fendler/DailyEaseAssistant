package pl.dawidfendler.data.model.transaction

import pl.dawidfendler.domain.model.transaction.Transaction

val transactionEntityTest = TransactionEntity(
    id = 1,
    amount = 500.0,
    date = 1762924800000, // 10.12.2025
    description = null,
    accountName = "Test Account",
    categoryName = "Test Category"
)

val transactionTest = Transaction(
    amount = 500.0,
    date = 1762924800000, // 10.12.2025
    description = null,
    accountName = "Test Account",
    categoryName = "Test Category"
)