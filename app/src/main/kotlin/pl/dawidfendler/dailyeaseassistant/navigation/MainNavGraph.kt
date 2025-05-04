package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheet
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetController
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetEvent
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetModel
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.dailyeaseassistant.components.MainTopAppBar
import pl.dawidfendler.navigation.financeManagerGraph
import pl.dawidfendler.main.homeRoute
import pl.dawidfendler.util.navigation.Destination

@Composable
fun MainNavGraph(navController: NavHostController) {
    var showBottomDialog by remember { mutableStateOf(CustomBottomSheetModel()) }

    ObserveAsEvents(CustomBottomSheetController.event) { events ->
        showBottomDialog = when (events) {
            is CustomBottomSheetEvent.ErrorBottomSheet -> {
                CustomBottomSheetModel(
                    showBottomSheet = true,
                    isSuccess = false,
                    title = events.title,
                    description = events.description,
                    icon = pl.dawidfendler.ui.R.drawable.ic_error,
                    iconTint = Color.Red
                )
            }

            is CustomBottomSheetEvent.SuccessBottomSheet -> {
                CustomBottomSheetModel(
                    showBottomSheet = true,
                    isSuccess = true,
                    title = events.title,
                    description = events.description,
                    icon = pl.dawidfendler.ui.R.drawable.ic_success,
                    iconTint = Color.Green
                )
            }
        }
    }

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
