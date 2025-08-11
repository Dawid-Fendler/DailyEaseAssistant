package pl.finance_managerV2.statistic

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType

fun NavGraphBuilder.statisticRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
) {
    composable<FinanceManagerBottomNavigationType.Statistics> {
        StatisticsScreen()
    }
}