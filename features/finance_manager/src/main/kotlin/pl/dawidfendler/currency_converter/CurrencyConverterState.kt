package pl.dawidfendler.currency_converter

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import java.math.BigDecimal

data class CurrencyConverterState(
    val mainCurrencyCode: String = "",
    val mainCurrencyMidValue: BigDecimal = BigDecimal.ZERO,
    val mainCurrencyValue: String = "",
    val mainCurrencyFullName: String = "",
    val mainCurrencyFlag: String = "",
    val secondCurrencyCode: String = "",
    val secondCurrencyMidValue: BigDecimal = BigDecimal.ZERO,
    val secondCurrencyValue: String = "",
    val secondCurrencyFullName : String = "",
    val secondCurrencyFlag: String = "",
    val currencies: List<ExchangeRateTable> = emptyList(),
    val exchangeRateDate: String = ""
)
