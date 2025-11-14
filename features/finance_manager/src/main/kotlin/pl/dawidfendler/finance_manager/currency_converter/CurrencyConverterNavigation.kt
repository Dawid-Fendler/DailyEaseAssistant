package pl.dawidfendler.finance_manager.currency_converter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.components.scaffold.DailyEaseAssistantScaffold
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.finance_manager.navigation.FinanceMangerNavigationType
import pl.dawidfendler.ui.theme.sp_24

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.currencyConverterRoute(onBackClick: () -> Unit) {
    composable<FinanceMangerNavigationType.CurrencyConverter> {
        val viewModel: CurrencyConverterViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle()
        DailyEaseAssistantScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        CustomText(
                            text = stringResource(R.string.currency_converter_title),
                            color = Color.Black,
                            fontSize = sp_24,
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                )
            }
        ) {
            CurrencyConverterScreen(
                modifier = Modifier.padding(it),
                state = state.value,
                onAction = viewModel::onAction
            )
        }
    }
}
