package pl.finance_managerV2.ai_assistant

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.bottom_bar.FinanceManagerBottomNavigationType

fun NavGraphBuilder.aiAssistantRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
) {
    composable<FinanceManagerBottomNavigationType.AiAssistant> {
        AiAssistantScreen()
    }
}