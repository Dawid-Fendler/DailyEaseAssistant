package pl.dawidfendler.finance_manager.statistic

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold
import pl.dawidfendler.finance_manager.components.bottom_bar.FinanceManagerBottomNavigationType

fun NavGraphBuilder.statisticRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    composable<FinanceManagerBottomNavigationType.Statistics> {
        DailyEaseAssistantScaffold(
            bottomBar = bottomBar
        ) {
            StatisticsScreen(
                modifier = modifier.padding(it)
            )
        }
    }
}