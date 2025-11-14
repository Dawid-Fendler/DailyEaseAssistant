package pl.dawidfendler.domain.util

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_VALUE

internal fun getPolishCurrency() = ExchangeRateTable(
    currencyName = POLISH_ZLOTY,
    currencyCode = POLISH_ZLOTY_CODE,
    currencyMidValue = POLISH_ZLOTY_VALUE
)
