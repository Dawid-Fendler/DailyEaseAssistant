package pl.finance_managerV2.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold

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