package pl.dawidfendler

import androidx.compose.ui.graphics.Color
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE

data class FinanceManagerState(
    val currencies: List<ExchangeRateTable> = emptyList(),
    val selectedCurrency: String = POLISH_ZLOTY_CODE,
    val accountBalance: String = "0",
    val accountBalanceColor: Color = Color.Black,
    val isCurrenciesFetchDataError: Boolean = false,
    val transaction: List<String> = emptyList(),
    val isTransactionFetchDataError: Boolean = false
)
