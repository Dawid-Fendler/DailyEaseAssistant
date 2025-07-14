package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheet
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetModel
import pl.dawidfendler.dailyeaseassistant.components.MainTopAppBar
import pl.dawidfendler.dailyeaseassistant.util.ObserveBottomSheetEvent
import pl.dawidfendler.dailyeaseassistant.util.ObserveMainTopBarVisibility
import pl.dawidfendler.main.homeRoute
import pl.dawidfendler.navigation.financeManagerGraph
import pl.dawidfendler.util.navigation.Destination

@Composable
fun MainNavGraph(navController: NavHostController) {
    var showBottomDialog by remember { mutableStateOf(CustomBottomSheetModel()) }
    ObserveBottomSheetEvent(
        updateCustomBottomModel = { model ->
            showBottomDialog = model
        }
    )

    var mainTopBarVisibility by rememberSaveable { mutableStateOf(true) }
    ObserveMainTopBarVisibility(
        updateMainTopBarVisibility = { visibility ->
            mainTopBarVisibility = visibility
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(mainTopBarVisibility) {
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
        }
    ) { innerPadding ->
        if (showBottomDialog.showBottomSheet) {
            CustomBottomSheet(
                title = showBottomDialog.title,
                description = showBottomDialog.description,
                icon = showBottomDialog.icon,
                iconTint = showBottomDialog.iconTint,
                onDismissAction = {
                    showBottomDialog = CustomBottomSheetModel()
                }
            )
        }
        NavHost(
            navController = navController,
            startDestination = Destination.Home
        ) {
            homeRoute(
                modifier = Modifier
                    .padding(innerPadding),
                navigateToFinanceManager = {
                    navController.navigate(Destination.FinanceManager)
                }
            )
            financeManagerGraph(
                modifier = Modifier
                    .padding(innerPadding),
                navController = navController
            )
        }
    }
}


