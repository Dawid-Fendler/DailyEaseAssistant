package pl.dawidfendler.navigation

import kotlinx.serialization.Serializable

internal sealed class FinanceMangerNavigationType {

    @Serializable
    data object FinanceMangerMain : FinanceMangerNavigationType()

    @Serializable
    data object CurrencyConverter : FinanceMangerNavigationType()
}