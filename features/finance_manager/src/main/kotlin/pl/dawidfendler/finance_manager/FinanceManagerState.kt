package pl.dawidfendler.finance_manager

import pl.dawidfendler.account_balance.UserCurrencies
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE

data class FinanceManagerState(
    val currencies: List<ExchangeRateTable> = emptyList(),
    val selectedCurrency: String = POLISH_ZLOTY_CODE,
    val isCurrenciesFetchDataError: Boolean = false,
    val transaction: List<String> = emptyList(),
    val isTransactionFetchDataError: Boolean = false,
    val userCurrencies: List<UserCurrencies> = emptyList(),
    val isLoading: Boolean = true
)
