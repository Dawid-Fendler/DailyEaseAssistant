package pl.dawidfendler.bottom_bar

import kotlinx.serialization.Serializable

sealed class FinanceManagerBottomNavigationType {

    @Serializable
    data object Transactions : FinanceManagerBottomNavigationType()

    @Serializable
    data object Statistics : FinanceManagerBottomNavigationType()

    @Serializable
    data object AiAssistant : FinanceManagerBottomNavigationType()

    @Serializable
    data object Settings : FinanceManagerBottomNavigationType()

    @Serializable
    data object Dashboard : FinanceManagerBottomNavigationType()
}
