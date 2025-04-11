package pl.dawidfendler.finance_manager

import androidx.compose.ui.graphics.Color
import pl.dawidfendler.account_balance.AccountBalanceItem
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE

data class FinanceManagerState(
    val currencies: List<ExchangeRateTable> = emptyList(),
    val selectedCurrency: String = POLISH_ZLOTY_CODE,
    val accountBalance: String = "0",
    val accountBalanceColor: Color = Color.White,
    val isCurrenciesFetchDataError: Boolean = false,
    val transaction: List<String> = emptyList(),
    val isTransactionFetchDataError: Boolean = false,
    val accountBalanceItems: List<AccountBalanceItem> = emptyList()
)
