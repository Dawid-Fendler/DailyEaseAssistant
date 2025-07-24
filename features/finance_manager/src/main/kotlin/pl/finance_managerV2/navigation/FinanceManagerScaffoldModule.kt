package pl.finance_managerV2.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.dawidfendler.currency_converter.currencyConverterRoute

@Composable
fun FinanceManagerScaffoldModule() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FinanceMangerNavigationType.FinanceManagerDashboard
        ) {
            financeManagerRoute(
                modifier = Modifier.padding(innerPadding),
                navigate = {
                    navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
                }
            )

            currencyConverterRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}