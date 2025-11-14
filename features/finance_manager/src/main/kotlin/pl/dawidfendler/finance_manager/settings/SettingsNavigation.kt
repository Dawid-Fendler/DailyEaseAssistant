package pl.dawidfendler.finance_manager.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold
import pl.dawidfendler.finance_manager.components.bottom_bar.FinanceManagerBottomNavigationType

fun NavGraphBuilder.settingsRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    composable<FinanceManagerBottomNavigationType.Settings> {
        DailyEaseAssistantScaffold(
            bottomBar = bottomBar
        ) {
            SettingsScreen(
                modifier = modifier.padding(it)
            )
        }
    }
}
