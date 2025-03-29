package pl.dawidfendler.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.dawidfendler.main.components.FinanceManagerWidget

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    navigateToFinanceManager: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        FinanceManagerWidget(navigateToFinanceManager = navigateToFinanceManager)
    }
}
