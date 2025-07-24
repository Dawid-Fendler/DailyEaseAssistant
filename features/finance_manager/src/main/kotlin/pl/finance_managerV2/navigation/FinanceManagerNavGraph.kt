package pl.finance_managerV2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.dawidfendler.util.navigation.Destination


fun NavGraphBuilder.financeManagerNavGraph() {
    navigation<Destination.FinanceManager>(
        startDestination = FinanceMangerNavigationType.FinanceManagerMain
    ) {
        composable<FinanceMangerNavigationType.FinanceManagerMain> {
            FinanceManagerScaffoldModule()
        }
    }
}