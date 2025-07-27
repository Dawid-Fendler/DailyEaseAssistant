package pl.finance_managerV2.dashboard

sealed interface DashboardAction {
    data object DataRefresh : DashboardAction
}