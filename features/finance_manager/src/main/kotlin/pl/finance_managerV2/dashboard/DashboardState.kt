package pl.finance_managerV2.dashboard

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.finance_managerV2.model.AccountUiModel

data class DashboardState(
    val isLoading: Boolean = true,
    val isFetchDataError: Boolean = false,
    val accounts: List<AccountUiModel> = emptyList(),
    val showAddAccountCard: Boolean = false,
    val finalTotalBalance: String = "0.0",
    val currencies: List<ExchangeRateTable> = emptyList(),
    val accountsCurrencyCode: List<String> = emptyList(),
    val addAccountBottomSheetVisibility: Boolean = false
)
