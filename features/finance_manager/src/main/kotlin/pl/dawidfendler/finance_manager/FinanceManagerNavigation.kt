package pl.dawidfendler.finance_manager

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
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.financeManagerRout(
    modifier: Modifier
) {

    composable<Navigation.FinanceManager> {
        val viewModel: FinanceManagerViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
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
            onAction = viewModel::onAction
        )
    }
}
