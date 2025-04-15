package pl.dawidfendler.account_balance.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.components.empty_screen.EmptyScreen
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.dp_0
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_400
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_14

@Composable
internal fun CurrenciesBottomSheet(
    modifier: Modifier = Modifier,
    currencies: List<ExchangeRateTable>,
    isCurrenciesFetchDataError: Boolean,
    selectedCurrency: String,
    onSelectCurrency: (String) -> Unit
) {
    CustomText(
        text = stringResource(R.string.currencies_bottom_dialog_title),
        modifier = Modifier
            .padding(dp_16),
        textAlign = TextAlign.Center
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(dp_400),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    bottom = if (isCurrenciesFetchDataError) {
                        dp_16
                    } else {
                        dp_0
                    }
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(currencies) { currency ->
                CurrencyItem(
                    currency = currency,
                    selectedCurrency = selectedCurrency,
                    onSelectCurrency = onSelectCurrency
                )
            }
        }

        if (isCurrenciesFetchDataError) {
            EmptyScreen()
        }
    }
}

@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: ExchangeRateTable,
    selectedCurrency: String,
    onSelectCurrency: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dp_16,
                vertical = dp_4
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(dp_16)
            )
            .border(
                width = dp_1,
                color = if (currency.currencyCode == selectedCurrency) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Black
                },
                shape = RoundedCornerShape(dp_16)
            )
            .clickable {
                onSelectCurrency(currency.currencyCode)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency.currencyName,
            color = if (currency.currencyCode == selectedCurrency) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Black
            },
            style = TextStyle(
                fontSize = sp_14,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = dp_8)
                .weight(1f)
        )

        RadioButton(
            selected = currency.currencyCode == selectedCurrency,
            onClick = {
                onSelectCurrency(currency.currencyCode)
            },
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}
