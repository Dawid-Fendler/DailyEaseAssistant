package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import pl.dawidfendler.util.navigation.Destination
import pl.finance_managerV2.navigation.financeManagerNavGraph

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
            AnimatedVisibility(
                visible = mainTopBarVisibility,
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = DURATION_MILLIS),
                    targetOffsetY = { -it }
                ) + fadeOut(
                    animationSpec = tween(durationMillis = FADE_OUT_DURATION_MILLIS)
                ),
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = DURATION_MILLIS),
                    initialOffsetY = { -it }
                ) + fadeIn(
                    animationSpec = tween(durationMillis = FADE_OUT_DURATION_MILLIS)
                )
            ) {
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
            financeManagerNavGraph()
        }
    }
}

private const val DURATION_MILLIS = 300
private const val FADE_OUT_DURATION_MILLIS = 250