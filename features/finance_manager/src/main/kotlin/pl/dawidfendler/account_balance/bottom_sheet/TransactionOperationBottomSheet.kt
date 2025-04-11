package pl.dawidfendler.account_balance.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.components.button.DailyEaseAssistantButton
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.components.text_field.MyTextField
import pl.dawidfendler.domain.util.Constants.PRICE_DROP_AMOUNT
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_300

@Composable
fun TransactionOperationBottomSheet(
    modifier: Modifier = Modifier,
    transactionOperations: TransactionOperations,
    moneyOperationOnClick: (String) -> Unit
) {
    var moneyValue by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(dp_300),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(
            text = if (transactionOperations == TransactionOperations.ADD) {
                stringResource(R.string.add_money)
            } else {
                stringResource(R.string.spent_money)
            },
            modifier = Modifier
                .padding(dp_16),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .height(dp_16)
                .weight(0.2f)
        )

        MyTextField(
            value = moneyValue,
            onValueChange = { valueChanged ->
                val filteredValue = valueChanged.filter { it.isDigit() || it == '.' }
                    .let {
                        if (it.count { char -> char == '.' } > PRICE_DROP_AMOUNT) {
                            it.dropLast(PRICE_DROP_AMOUNT)
                        } else {
                            it
                        }
                    }

                moneyValue = filteredValue
            },
            hintText = if (transactionOperations == TransactionOperations.ADD) {
                stringResource(R.string.add_money)
            } else {
                stringResource(R.string.spent_money)
            },
            leadingIcon = R.drawable.ic_money,
            keyboardType = KeyboardType.Number
        )

        Spacer(
            modifier = Modifier
                .height(dp_16)
                .weight(0.5f)
        )

        DailyEaseAssistantButton(
            name = stringResource(pl.dawidfendler.ui.R.string.confirm_button_title),
            onClick = { moneyOperationOnClick(moneyValue) },
            modifier = Modifier
                .padding(bottom = dp_16),
            isEnabled = moneyValue.isNotBlank()
        )
    }
}
