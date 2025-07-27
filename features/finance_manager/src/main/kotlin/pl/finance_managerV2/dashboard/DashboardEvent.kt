package pl.finance_managerV2.dashboard

sealed interface DashboardEvent {
    data class ShowErrorBottomDialog(val errorMessage: String?) : DashboardEvent
}