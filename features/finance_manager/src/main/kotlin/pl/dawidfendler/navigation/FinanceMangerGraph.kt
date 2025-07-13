package pl.dawidfendler.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import pl.dawidfendler.currency_converter.currencyConverterRoute
import pl.dawidfendler.util.navigation.Destination

fun NavGraphBuilder.financeManagerGraph(navController: NavController, modifier: Modifier) {
    navigation<Destination.FinanceManager>(
        startDestination = FinanceMangerNavigationType.FinanceMangerMain
    ) {
        financeManagerRoute(
            modifier,
            navigate = {
                navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
            }
        )

        currencyConverterRoute(
            onBackClick = { navController.popBackStack() }
        )
    }
}