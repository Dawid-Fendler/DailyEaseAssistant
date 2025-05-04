package pl.dawidfendler.currency_converter

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.navigation.FinanceMangerNavigationType


fun NavGraphBuilder.currencyConverterRoute() {
    composable<FinanceMangerNavigationType.CurrencyConverter> {
        val viewModel: CurrencyConverterViewModel = hiltViewModel()
    }
}