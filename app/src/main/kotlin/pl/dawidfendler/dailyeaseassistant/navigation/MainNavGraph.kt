package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import pl.dawidfendler.main.homeRoute
import pl.dawidfendler.util.navigation.Destination
import pl.dawidfendler.finance_manager.navigation.FinanceMangerNavigationType
import pl.dawidfendler.finance_manager.navigation.financeManagerNavGraph

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation<Destination.MainGraph>(
        startDestination = Destination.Home
    ) {
        homeRoute(
            modifier = Modifier,
            navigateToFinanceManager = {
                navController.navigate(FinanceMangerNavigationType.FinanceManagerMain)
            }
        )
        financeManagerNavGraph()
    }
}

private const val DURATION_MILLIS = 300
private const val FADE_OUT_DURATION_MILLIS = 250
