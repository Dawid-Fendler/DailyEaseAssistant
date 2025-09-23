package pl.finance_managerV2.dashboard

sealed interface DashboardAction {
    data object DataRefresh : DashboardAction
    data class ChangeAddAccountBottomSheetVisibility(val isVisible: Boolean) : DashboardAction
    data class AddNewAccount(val accountName: String, val currencyCode: String) : DashboardAction
}