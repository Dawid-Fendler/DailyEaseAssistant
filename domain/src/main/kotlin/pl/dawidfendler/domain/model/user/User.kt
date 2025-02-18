package pl.dawidfendler.domain.model.user

import java.math.BigDecimal

data class User(
    val accountBalance: BigDecimal = BigDecimal.ZERO
)
