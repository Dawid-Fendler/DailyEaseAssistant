package pl.finance_managerV2.dashboard

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetController
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetEvent
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.domain.util.Constants.ERROR_TITLE
import pl.dawidfendler.domain.util.Constants.SOMETHING_WENT_WRONG
import pl.dawidfendler.util.controller.MainTopBarVisibilityController.sendMainTopBarEvent
import pl.dawidfendler.util.controller.MainTopBarVisibilityEvent
import pl.finance_managerV2.navigation.FinanceMangerNavigationType

fun NavGraphBuilder.dashboardRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
) {
    composable<FinanceMangerNavigationType.Dashboard> {
        val viewModel: DashboardViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        val state = viewModel.state.collectAsStateWithLifecycle()
        scope.launch {
            sendMainTopBarEvent(MainTopBarVisibilityEvent.HideMainTopBar)
        }
        ObserveAsEvents(flow = viewModel.eventChannel) { event ->
            when (event) {
                is DashboardEvent.ShowErrorBottomDialog -> {
                    scope.launch {
                        CustomBottomSheetController.sendEvent(
                            CustomBottomSheetEvent.ErrorBottomSheet(
                                title = ERROR_TITLE,
                                description = event.errorMessage ?: SOMETHING_WENT_WRONG
                            )
                        )
                    }
                }
            }
        }


        DashboardScreen(
            modifier = modifier,
            state = state.value,
            onAction = viewModel::onAction
        )
    }
}