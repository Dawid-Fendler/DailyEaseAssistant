package pl.dawidfendler.account_balance.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.account_balance.UserCurrencies
import pl.dawidfendler.components.button.DailyEaseAssistantButton
import pl.dawidfendler.components.empty_screen.EmptyScreen
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.dp_0
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_14

@Composable
fun AccountBalanceCurrenciesBottomSheet(
    modifier: Modifier = Modifier,
    currencies: List<ExchangeRateTable>,
    isCurrenciesFetchDataError: Boolean,
    selectedCurrency: List<UserCurrencies>,
    onConfirmClick: (List<String>) -> Unit
) {

    val userSelectedCurrencies = remember {
        mutableStateListOf<String>().apply {
            addAll(selectedCurrency.map { it.currencyName })
        }
    }

    CustomText(
        text = stringResource(R.string.account_balance_currencies_bottom_dialog_title, userSelectedCurrencies.size),
        modifier = Modifier
            .padding(dp_16),
        textAlign = TextAlign.Center
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.9f)
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
                AccountBalanceCurrencyItem(
                    currency = currency,
                    userSelectedCurrencies = userSelectedCurrencies,
                    onUserSelectedCurrencies = { newCurrency, isAdd ->
                        if (isAdd) {
                            if (userSelectedCurrencies.size < 6) {
                                userSelectedCurrencies.add(newCurrency)
                            }
                        } else {
                            if (userSelectedCurrencies.size > 1) {
                                userSelectedCurrencies.remove(newCurrency)
                            }
                        }
                    }
                )
            }
        }

        DailyEaseAssistantButton(
            name = stringResource(pl.dawidfendler.ui.R.string.confirm_button_title),
            onClick = { onConfirmClick.invoke(userSelectedCurrencies) },
            modifier = Modifier
                .padding(bottom = dp_16)
                .weight(0.1f)
        )

        if (isCurrenciesFetchDataError) {
            EmptyScreen()
        }
    }
}

@Composable
fun AccountBalanceCurrencyItem(
    modifier: Modifier = Modifier,
    currency: ExchangeRateTable,
    userSelectedCurrencies: SnapshotStateList<String>,
    onUserSelectedCurrencies: (String, Boolean) -> Unit
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
                color = if (userSelectedCurrencies.contains(currency.currencyCode)) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Black
                },
                shape = RoundedCornerShape(dp_16)
            )
            .clickable{
                onUserSelectedCurrencies.invoke(
                    currency.currencyCode,
                    !userSelectedCurrencies.contains(currency.currencyCode)
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency.currencyName,
            color = if (userSelectedCurrencies.contains(currency.currencyCode)) {
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

        Checkbox(
            checked = userSelectedCurrencies.contains(currency.currencyCode),
            colors = CheckboxDefaults.colors().copy(
                checkedBoxColor = MaterialTheme.colorScheme.primary
            ),
            onCheckedChange = {
                onUserSelectedCurrencies.invoke(
                    currency.currencyCode,
                    !userSelectedCurrencies.contains(currency.currencyCode)
                )
            }
        )
    }
}
