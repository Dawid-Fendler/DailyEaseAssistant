package pl.dawidfendler.finance_manager.ai_assistant

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.finance_manager.components.bottom_bar.FinanceManagerBottomNavigationType
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold

fun NavGraphBuilder.aiAssistantRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    composable<FinanceManagerBottomNavigationType.AiAssistant> {
        DailyEaseAssistantScaffold(
            bottomBar = bottomBar
        ) {
            AiAssistantScreen(
                modifier = modifier.padding(it)
            )
        }
    }
}