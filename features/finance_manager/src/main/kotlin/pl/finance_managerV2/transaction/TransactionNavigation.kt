package pl.finance_managerV2.transaction

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType
import pl.finance_managerV2.statistic.StatisticsScreen

fun NavGraphBuilder.transactionRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
) {
    composable<FinanceManagerBottomNavigationType.Transactions> {
        StatisticsScreen()
    }
}