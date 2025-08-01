package pl.dawidfendler.main

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.util.navigation.Destination

fun NavGraphBuilder.homeRoute(
    navigateToFinanceManager: () -> Unit,
    modifier: Modifier
) {
    composable<Destination.Home> {
        val viewModel: HomeViewModel = hiltViewModel()

        HomeScreen(
            modifier = modifier,
            state = viewModel.state,
            navigateToFinanceManager = navigateToFinanceManager
        )
    }
}
