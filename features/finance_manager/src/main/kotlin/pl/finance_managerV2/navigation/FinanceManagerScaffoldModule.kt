package pl.finance_managerV2.navigation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType
import pl.dawidfendler.bottom_bar.buildBottomNavItems
import pl.dawidfendler.currency_converter.currencyConverterRoute
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.util.setStatusBarColor
import pl.finance_managerV2.ai_assistant.aiAssistantRoute
import pl.finance_managerV2.components.FinanceManagerBottomBar
import pl.finance_managerV2.dashboard.dashboardRoute
import pl.finance_managerV2.settings.settingsRoute
import pl.finance_managerV2.statistic.statisticRoute
import pl.finance_managerV2.transaction.transactionRoute

@Composable
fun FinanceManagerScaffoldModule() {
    val navController = rememberNavController()
    val view = LocalView.current
    val window = (view.context as? Activity)?.window ?: return

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        bottomBar = {
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
    ) { innerPadding ->
        DisposableEffect(Unit) {
            setStatusBarColor(window, Color.White.toArgb())
            onDispose {
                setStatusBarColor(window, MD_THEME_LIGHT_PRIMARY.toArgb())
            }
        }
        NavHost(
            navController = navController,
            startDestination = FinanceManagerBottomNavigationType.Dashboard
        ) {
            dashboardRoute(
                modifier = Modifier.padding(innerPadding),
                navigate = {
                    navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
                }
            )

            transactionRoute(
                modifier = Modifier.padding(innerPadding),
                navigate = {
                    navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
                }
            )

            statisticRoute(
                modifier = Modifier.padding(innerPadding),
                navigate = {
                    navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
                }
            )

            aiAssistantRoute(
                modifier = Modifier.padding(innerPadding),
                navigate = {
                    navController.navigate(FinanceMangerNavigationType.CurrencyConverter)
                }
            )

            settingsRoute(
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