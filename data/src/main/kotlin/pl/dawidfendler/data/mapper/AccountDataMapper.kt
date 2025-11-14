package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.account.AccountEntity
import pl.dawidfendler.data.model.user.UserWithAccounts
import pl.dawidfendler.domain.model.account.Account

fun UserWithAccounts.toDomain(): pl.dawidfendler.domain.model.account.UserWithAccounts {
    return pl.dawidfendler.domain.model.account.UserWithAccounts(
        user = user.toDomain(),
        accounts = accounts.map { it.toDomain() }
    )
}

fun AccountEntity.toDomain(): Account {
    return Account(
        currencyCode = currencyCode,
        balance = balance,
        isMainAccount = isMainAccount,
        accountName = accountName
    )
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        ownerUserId = 1,
        currencyCode = currencyCode,
        balance = balance,
        isMainAccount = isMainAccount,
        accountName = accountName
    )
}
