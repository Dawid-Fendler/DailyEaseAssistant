package pl.finance_managerV2.dashboard

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.finance_managerV2.model.AccountUiModel

data class DashboardState(
    val isLoading: Boolean = true,
    val currencies: List<ExchangeRateTable> = emptyList(),
    val isCurrenciesFetchDataError: Boolean = false,
    val accounts: List<AccountUiModel> = emptyList(),
    val showAddAccountCard: Boolean = false
)
