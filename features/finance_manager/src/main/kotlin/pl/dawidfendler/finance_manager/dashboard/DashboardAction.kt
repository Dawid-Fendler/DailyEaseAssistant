package pl.dawidfendler.finance_manager.dashboard

sealed interface DashboardAction {
    data object DataRefresh : DashboardAction
    data class ChangeAddAccountBottomSheetVisibility(val isVisible: Boolean) : DashboardAction
    data class AddNewAccount(val accountName: String, val currencyCode: String) : DashboardAction
    data class ChangeTransactionOptionDialogVisibility(
        val isVisible: Boolean,
        val isExpense: Boolean = false
    ) : DashboardAction

    data class OnSaveTransactionClick(
        val selectedAccount: String,
        val date: Long,
        val amount: String,
        val description: String,
        val categoryName: String,
        val isExpense: Boolean
    ) : DashboardAction
}
