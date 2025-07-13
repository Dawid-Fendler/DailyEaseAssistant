package pl.dawidfendler.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetController
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetEvent
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.domain.util.Constants.ERROR_TITLE
import pl.dawidfendler.domain.util.Constants.SOMETHING_WENT_WRONG
import pl.dawidfendler.finance_manager.FinanceManagerEvent
import pl.dawidfendler.finance_manager.FinanceManagerScreen
import pl.dawidfendler.finance_manager.FinanceManagerViewModel
import pl.dawidfendler.util.ShowSystemBars
import pl.dawidfendler.util.controller.MainTopBarVisibilityController
import pl.dawidfendler.util.controller.MainTopBarVisibilityEvent

fun NavGraphBuilder.financeManagerRoute(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
) {
    composable<FinanceMangerNavigationType.FinanceMangerMain> {
        val viewModel: FinanceManagerViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()

        scope.launch {
            MainTopBarVisibilityController.sendMainTopBarEvent(MainTopBarVisibilityEvent.ShowMainTopBar)
        }
        ShowSystemBars()

        ObserveAsEvents(flow = viewModel.eventChannel) { event ->
            when (event) {
                is FinanceManagerEvent.ShowErrorBottomDialog -> {
                    scope.launch {
                        CustomBottomSheetController.sendEvent(
                            CustomBottomSheetEvent.ErrorBottomSheet(
                                title = ERROR_TITLE,
                                description = event.errorMessage ?: SOMETHING_WENT_WRONG
                            )
                        )
                    }
                }
            }
        }

        FinanceManagerScreen(
            modifier = modifier,
            state = viewModel.state,
            onAction = viewModel::onAction,
            navigate
        )
    }
}
