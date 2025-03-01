package pl.dawidfendler.finance_manager.mapper

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun formatAccountBalance(accountBalance: BigDecimal): String {
    return NumberFormat.getNumberInstance(Locale.FRANCE).format(accountBalance)
}
