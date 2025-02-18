package pl.dawidfendler.finance_manager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import pl.dawidfendler.components.button.CustomButton
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_2
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.sp_20
import pl.dawidfendler.ui.theme.sp_36

@Composable
fun AccountBalance(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
    onHistoryClick: () -> Unit,
    accountBalance: String,
    selectedCurrencyCode: String,
    accountBalanceColor: Color
) {
    Card(
        modifier = modifier
            .padding(horizontal = dp_16)
            .padding(top = dp_16),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = dp_4
        ),
        shape = RoundedCornerShape(dp_24),
        border = BorderStroke(
            width = dp_2,
            color = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.inverseOnSurface
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            CustomText(
                text = stringResource(R.string.account_balance_title),
                modifier = Modifier
                    .padding(
                        top = dp_16,
                        start = dp_16
                    )
            )

            CustomText(
                text = stringResource(
                    pl.dawidfendler.ui.R.string.account_balance_money_format_code,
                    accountBalance,
                    selectedCurrencyCode
                ),
                fontSize = if (accountBalance.length > 9) {
                    sp_20
                } else {
                    sp_36
                },
                color = accountBalanceColor,
                modifier = Modifier
                    .padding(
                        top = dp_16,
                        start = dp_16,
                        end = dp_16
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dp_16,
                        horizontal = dp_48
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    modifier = Modifier,
                    icon = Icons.Default.Add,
                    onClick = {
                        onAddClick.invoke()
                    },
                    text = stringResource(R.string.add),
                    showText = true,
                )

                CustomButton(
                    modifier = Modifier,
                    icon = Icons.Default.Remove,
                    onClick = {
                        onMinusClick.invoke()
                    },
                    text = stringResource(R.string.remove),
                    showText = true
                )

                CustomButton(
                    modifier = Modifier,
                    icon = Icons.Default.CurrencyExchange,
                    onClick = {
                        onCurrenciesClick.invoke()
                    },
                    text = stringResource(R.string.currency),
                    showText = true
                )

                CustomButton(
                    modifier = Modifier,
                    icon = Icons.Default.History,
                    onClick = {
                        onHistoryClick.invoke()
                    },
                    text = stringResource(R.string.history),
                    showText = true
                )
            }
        }
    }
}