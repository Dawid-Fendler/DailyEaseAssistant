package pl.dawidfendler.finance_manager

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.financeManagerRout(
    modifier: Modifier
) {

    composable<Navigation.FinanceManager> {
        val viewModel: FinanceManagerViewModel = hiltViewModel()

        FinanceManagerScreen(
            modifier = modifier
        )
    }
}