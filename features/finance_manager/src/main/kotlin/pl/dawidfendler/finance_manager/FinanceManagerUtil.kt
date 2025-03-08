package pl.dawidfendler.finance_manager

import pl.dawidfendler.domain.util.Constants.MINUS
import pl.dawidfendler.domain.util.Constants.PLUS
import java.time.OffsetDateTime

internal fun prepareTransactionContent(
    money: String,
    isAdd: Boolean,
    date: OffsetDateTime
): String {
    val sign = if (isAdd) {
        PLUS
    } else {
        MINUS
    }

    return "Date:$date - Transaction:$sign$money"
}