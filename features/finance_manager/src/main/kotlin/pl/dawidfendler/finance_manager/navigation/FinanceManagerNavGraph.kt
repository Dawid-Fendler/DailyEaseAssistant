package pl.dawidfendler.finance_manager.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.financeManagerNavGraph() {
    composable<FinanceMangerNavigationType.FinanceManagerMain> {
        FinanceManagerFeature()
    }
}
