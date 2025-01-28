package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.finance_manager.financeManagerRout
import pl.dawidfendler.dailyeaseassistant.components.MainTopAppBar
import pl.dawidfendler.main.homeRoute
import pl.dawidfendler.util.navigation.Navigation

@Composable
fun MainNavGraph(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(
                // TODO add logic
                name = "David",
                onUserIconClick = {
                    // TODO add logic
                },
                onMenuItemClick = {
                    // TODO add logic
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Navigation.HomeNavigation
        ) {
            homeRoute(
                modifier = Modifier
                    .padding(innerPadding),
                navigateToFinanceManager = {
                    navController.navigate(Navigation.FinanceManager)
                }
            )

            financeManagerRout(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}