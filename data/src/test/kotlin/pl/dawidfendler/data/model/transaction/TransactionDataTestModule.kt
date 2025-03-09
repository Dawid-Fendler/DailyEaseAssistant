package pl.dawidfendler.data.model.transaction

import pl.dawidfendler.domain.model.transaction.Transaction

val transactionEntityTest = TransactionEntity(
    content = "10.12.2025 - + 500.0 zł"
)

val transactionTest = Transaction(
    content = "10.12.2025 - + 500.0 zł"
)