package pl.finance_managerV2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.financeManagerNavGraph() {
    composable<FinanceMangerNavigationType.FinanceManagerMain> {
        FinanceManagerFeature()
    }
}
