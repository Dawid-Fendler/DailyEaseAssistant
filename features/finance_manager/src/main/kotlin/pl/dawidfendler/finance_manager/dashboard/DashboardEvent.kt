package pl.dawidfendler.finance_manager.dashboard

sealed interface DashboardEvent {
    data class ShowErrorBottomDialog(val errorMessage: String?) : DashboardEvent
}