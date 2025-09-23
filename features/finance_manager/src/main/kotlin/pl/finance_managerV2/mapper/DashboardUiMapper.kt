package pl.finance_managerV2.mapper

import androidx.compose.ui.util.fastFirst
import pl.dawidfendler.domain.model.account.Account
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.finance_managerV2.model.AccountUiModel

fun Account.toUiModel(currencies: List<ExchangeRateTable>): AccountUiModel {
    val currency = currencies.fastFirst { it.currencyCode == this.currencyCode }
    return AccountUiModel(
        name = currency.currencyName,
        currency = currency.currencyCode,
        balance = this.balance.toString(),
        lastTransactionBalance = null,
        lastTransactionName = null,
        isExpense = false,
        isMainAccount = isMainAccount,
        mainName = accountName
    )
}