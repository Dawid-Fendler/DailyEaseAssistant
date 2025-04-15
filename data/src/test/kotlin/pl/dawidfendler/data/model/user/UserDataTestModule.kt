package pl.dawidfendler.data.model.user

import pl.dawidfendler.domain.model.user.User

val userEntityTest = UserEntity(
    accountBalance = 500.0,
    currencies = "PLN-USD-GBP"
)

val userTest = User(
    accountBalance = 500.0.toBigDecimal(),
    currencies = listOf("PLN","USD","GBP")
)