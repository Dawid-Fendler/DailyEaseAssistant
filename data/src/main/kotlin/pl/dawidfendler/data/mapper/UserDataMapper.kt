package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.currency.local.user.UserEntity
import pl.dawidfendler.domain.model.user.User

internal fun User.toEntity() = UserEntity(
    accountBalance = accountBalance.toDouble()
)

internal fun UserEntity.toDomain() = User(
    accountBalance = accountBalance.toBigDecimal()
)