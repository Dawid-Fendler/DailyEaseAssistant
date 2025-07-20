package pl.dawidfendler.currency_converter

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetController
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetEvent
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.domain.util.Constants.ERROR_TITLE
import pl.dawidfendler.domain.util.Constants.SOMETHING_WENT_WRONG
import pl.dawidfendler.finance_manager.FinanceManagerEvent
import pl.dawidfendler.navigation.FinanceMangerNavigationType
import pl.dawidfendler.util.HideSystemBars
import pl.dawidfendler.util.controller.MainTopBarVisibilityController
import pl.dawidfendler.util.controller.MainTopBarVisibilityEvent

fun NavGraphBuilder.currencyConverterRoute(onBackClick: () -> Unit) {
    composable<FinanceMangerNavigationType.CurrencyConverter> {

        val viewModel: CurrencyConverterViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        val query by viewModel.query.collectAsState()
        val filteredCurrencies by viewModel.filteredCurrencies.collectAsStateWithLifecycle()

        HideSystemBars()
        scope.launch {
            MainTopBarVisibilityController.sendMainTopBarEvent(MainTopBarVisibilityEvent.HideMainTopBar)
        }

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

        CurrencyConverterScreen(
            onBackClick = onBackClick,
            state = viewModel.state,
            onAction = viewModel::onAction,
            query = query,
            filteredCurrencies = filteredCurrencies
        )
    }
}