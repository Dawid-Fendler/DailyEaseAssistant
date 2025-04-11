package pl.dawidfendler.finance_manager.util

import pl.dawidfendler.domain.util.Constants.MINUS
import pl.dawidfendler.domain.util.Constants.PLUS

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
