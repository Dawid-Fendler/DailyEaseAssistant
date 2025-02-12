package pl.dawidfendler

import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY

data class FinanceManagerState(
    val currencies: List<String> = emptyList(),
    val selectedCurrencies: String = POLISH_ZLOTY
)
