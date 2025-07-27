package pl.finance_managerV2.dashboard

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable

data class DashboardState(
    val isLoading: Boolean = true,
    val currencies: List<ExchangeRateTable> = emptyList(),
    val isCurrenciesFetchDataError: Boolean = false
)
