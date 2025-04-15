package pl.dawidfendler.finance_manager.mapper

import pl.dawidfendler.account_balance.UserCurrencies
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.util.Constants.CURRENCY_DECIMAL_PLACES
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

internal fun formatAccountBalance(accountBalance: BigDecimal): String {
    return NumberFormat.getNumberInstance(Locale.FRANCE).format(accountBalance)
}

internal fun prepareUserCurrencies(
    currencies: List<ExchangeRateTable>,
    userCurrencies: List<String>,
    accountBalance: BigDecimal,
    selectedCurrencies: String,
    firstUserCurrencies: UserCurrencies
): List<UserCurrencies> {
    val onlyUserCurrencies = currencies.filter {
        userCurrencies.filter { userCurrencies -> userCurrencies != POLISH_ZLOTY_CODE }
            .contains(it.currencyCode)
    }
    val newUserCurrencies = buildList {
        add(firstUserCurrencies)
        onlyUserCurrencies.forEach {
            val item = UserCurrencies(
                currencyName = it.currencyCode,
                accountBalance = if (it.currencyCode == POLISH_ZLOTY_CODE) {
                    accountBalance.toString()
                } else {
                    (accountBalance * it.currencyMidValue?.toBigDecimal()!!)
                        .setScale(CURRENCY_DECIMAL_PLACES, RoundingMode.HALF_UP)
                        .stripTrailingZeros()
                        .toString()
                },
                isDebt = if (it.currencyCode == POLISH_ZLOTY_CODE) {
                    accountBalance < BigDecimal.ZERO
                } else {
                    (accountBalance * it.currencyMidValue?.toBigDecimal()!!) < BigDecimal.ZERO
                },
                isMainItem = selectedCurrencies == it.currencyCode
            )
            add(item)
        }
    }

    return newUserCurrencies
}
