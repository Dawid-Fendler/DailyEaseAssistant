package pl.dawidfendler.finance_manager.util

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.util.Constants.MINUS
import pl.dawidfendler.domain.util.Constants.PLUS
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_VALUE

internal fun prepareTransactionContent(
    money: String,
    isAdd: Boolean,
    date: String
): String {
    val sign = if (isAdd) {
        PLUS
    } else {
        MINUS
    }

    return "Date:$date - Transaction: $sign$money"
}

internal fun getPolishCurrency() = ExchangeRateTable(
    currencyName = POLISH_ZLOTY,
    currencyCode = POLISH_ZLOTY_CODE,
    currencyMidValue = POLISH_ZLOTY_VALUE
)
