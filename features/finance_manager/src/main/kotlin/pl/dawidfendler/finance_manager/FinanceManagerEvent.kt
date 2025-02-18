package pl.dawidfendler.finance_manager

sealed interface FinanceManagerEvent {
    data class ShowErrorBottomDialog(val errorMessage: String?) : FinanceManagerEvent
}