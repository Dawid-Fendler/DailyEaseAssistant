package pl.finance_managerV2.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetController
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetEvent
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.domain.util.Constants.ERROR_TITLE
import pl.dawidfendler.domain.util.Constants.SOMETHING_WENT_WRONG
import pl.dawidfendler.util.controller.MainTopBarVisibilityController.sendMainTopBarEvent
import pl.dawidfendler.util.controller.MainTopBarVisibilityEvent
import pl.dawidfendler.util.navigation.Destination

fun NavGraphBuilder.dashboardRoute(
    modifier: Modifier = Modifier,
    navigate: (Destination) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    composable<FinanceManagerBottomNavigationType.Dashboard> {
        val viewModel: DashboardViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(flow = viewModel.eventChannel) { event ->
            when (event) {
                is DashboardEvent.ShowErrorBottomDialog -> {
                }
            }
        }

        DailyEaseAssistantScaffold(
            bottomBar = bottomBar
        ) {
            DashboardScreen(
                modifier = modifier.padding(it),
                state = state.value,
                onAction = viewModel::onAction
            )
        }
    }
}