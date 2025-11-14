package pl.dawidfendler.finance_manager.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface FinanceMangerNavigationType {

    @Serializable
    data object FinanceManagerDashboard : FinanceMangerNavigationType

    @Serializable
    data object CurrencyConverter : FinanceMangerNavigationType

    @Serializable
    data object FinanceManagerMain : FinanceMangerNavigationType
}
