package pl.dawidfendler.finance_manager

sealed interface FinanceManagerAction {
    data object GetInitialData: FinanceManagerAction
    data class SavedSelectedCurrencies(val selectedCurrencies: String) : FinanceManagerAction
    data object GetSelectedCurrencies : FinanceManagerAction
    data class AddMoney(val addMoney: String) : FinanceManagerAction
    data class SpentMoney(val spentMoney: String) : FinanceManagerAction
    data object GetTransaction : FinanceManagerAction
    data class UpdateUserSelectedCurrencies(val userCurrencies: List<String>): FinanceManagerAction
}
