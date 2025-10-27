package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.transaction.TransactionEntity
import pl.dawidfendler.domain.model.transaction.Transaction

internal fun TransactionEntity.toDomain() = Transaction(
    date = date,
    amount = amount,
    description = description,
    accountName = accountName,
    categoryName = categoryName
)

internal fun Transaction.toEntity() = TransactionEntity(
    date = date,
    amount = amount,
    description = description,
    accountName = accountName,
    categoryName = categoryName
)
