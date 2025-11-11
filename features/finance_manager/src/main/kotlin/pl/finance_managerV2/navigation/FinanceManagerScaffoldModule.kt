package pl.finance_managerV2.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType
import pl.dawidfendler.bottom_bar.buildBottomNavItems
import pl.dawidfendler.currency_converter.currencyConverterRoute
import pl.dawidfendler.ui.theme.dp_1
import pl.finance_managerV2.ai_assistant.aiAssistantRoute
import pl.finance_managerV2.components.bottom_bar.FinanceManagerBottomBar
import pl.finance_managerV2.dashboard.dashboardRoute
import pl.finance_managerV2.settings.settingsRoute
import pl.finance_managerV2.statistic.statisticRoute
import pl.finance_managerV2.transaction.transactionRoute

@Composable
fun FinanceManagerFeature() {
    val navController = rememberNavController()
    val bottomBar: @Composable () -> Unit = {
        prepareBottomBar(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = FinanceManagerBottomNavigationType.Dashboard
    ) {
        dashboardRoute(
            navigate = {
                navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
            }, bottomBar = bottomBar
        )

        transactionRoute(
            navigate = {
                navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
            }, bottomBar = bottomBar
        )

        statisticRoute(
            navigate = {
                navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
            }, bottomBar = bottomBar
        )

        aiAssistantRoute(
            navigate = {
                navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
            }, bottomBar = bottomBar
        )

        settingsRoute(
            navigate = {
                navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
            }, bottomBar = bottomBar
        )

        currencyConverterRoute(
            onBackClick = { navController.popBackStack() })
    }
}

@Composable
private fun prepareBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentSelectedItem = buildBottomNavItems().find { item ->
        currentDestination?.hierarchy?.any {
            it.route == item.destination::class.qualifiedName
        } == true
    }

    Column {
        HorizontalDivider(
            thickness = dp_1,
            color = Color.Gray.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = dp_1)
        )

        FinanceManagerBottomBar(
            items = buildBottomNavItems(),
            currentSelectedItem = currentSelectedItem,
            onItemSelected = { item ->
                navController.navigate(item.destination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}