package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.transaction.TransactionEntity
import pl.dawidfendler.domain.model.transaction.Transaction

internal fun TransactionEntity.toDomain() = Transaction(
    content = content
)

internal fun Transaction.toEntity() = TransactionEntity(
    content = content
)