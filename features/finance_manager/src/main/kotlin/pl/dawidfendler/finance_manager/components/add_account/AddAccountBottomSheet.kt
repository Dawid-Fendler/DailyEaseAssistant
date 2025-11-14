package pl.dawidfendler.finance_manager.components.add_account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.finance_manager.components.currency_picker.CurrencyPicker
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_32
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_40
import pl.dawidfendler.ui.theme.dp_56
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_16
import pl.dawidfendler.ui.theme.sp_18
import pl.dawidfendler.ui.theme.sp_28

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountBottomSheet(
    modifier: Modifier = Modifier,
    onAddAccountClick: (String, String) -> Unit,
    currencies: List<ExchangeRateTable>
) {
    val scope = rememberCoroutineScope()
    var accountName by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf<String?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSelectCurrencyBottomSheet by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dp_16,
                vertical = dp_8
            ),
        verticalArrangement = Arrangement.spacedBy(dp_32)
    ) {
        CustomText(
            text = stringResource(R.string.add_account),
            letterSpacing = sp_0,
            fontSize = sp_28,
            color = Color.Black,
            modifier = Modifier.padding(vertical = dp_4)
        )

        OutlinedTextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = {
                CustomText(
                    text = stringResource(R.string.account_name),
                    letterSpacing = sp_0,
                    fontSize = sp_16,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = dp_4)
                )
            },
            shape = RoundedCornerShape(dp_40),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { showSelectCurrencyBottomSheet = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(dp_56)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(
                    text = selectedCurrency ?: stringResource(R.string.select_currency),
                    letterSpacing = sp_0,
                    fontSize = sp_16,
                    color = if (selectedCurrency == null) MD_THEME_LIGHT_PRIMARY else Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = dp_4)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = if (selectedCurrency == null) MD_THEME_LIGHT_PRIMARY else Color.Black
                )
            }
        }
        Button(
            onClick = { onAddAccountClick(accountName, selectedCurrency.orEmpty()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(dp_56),
            enabled = accountName.isNotEmpty() && selectedCurrency != null
        ) {
            CustomText(
                text = stringResource(R.string.add_account),
                letterSpacing = sp_0,
                fontSize = sp_18,
                color = Color.White,
                modifier = Modifier.padding(vertical = dp_4)
            )
        }
    }

    AnimatedVisibility(showSelectCurrencyBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showSelectCurrencyBottomSheet = false },
            dragHandle = null
        ) {
            CurrencyPicker(
                onCurrencySelected = { currency ->
                    selectedCurrency = currency
                    showSelectCurrencyBottomSheet = false
                    scope.launch {
                        sheetState.hide()
                    }
                },
                currencies = currencies
            )
        }
    }
}
