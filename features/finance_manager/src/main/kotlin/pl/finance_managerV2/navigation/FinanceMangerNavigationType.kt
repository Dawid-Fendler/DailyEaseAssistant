package pl.finance_managerV2.navigation

import kotlinx.serialization.Serializable

sealed class FinanceMangerNavigationType {

    @Serializable
    data object FinanceManagerDashboard : FinanceMangerNavigationType()

    @Serializable
    data object CurrencyConverter : FinanceMangerNavigationType()

    @Serializable
    data object FinanceManagerMain: FinanceMangerNavigationType()
}