package pl.dawidfendler.finance_manager.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.finance_manager.components.bottom_bar.FinanceManagerBottomNavigationType

fun NavGraphBuilder.dashboardRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
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
                onAction = viewModel::onAction,
                navigate = navigate
            )
        }
    }
}
