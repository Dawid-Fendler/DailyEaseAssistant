package pl.dawidfendler.domain.use_case.user

import pl.dawidfendler.domain.model.user.User

val userData = User(
    accountBalance = 500.0.toBigDecimal(),
    currencies = listOf("PLN", "USD")
)