package pl.dawidfendler.finance_manager.currency_converter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.finance_manager.components.currency_picker.CurrencyPicker
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE_VARIANT
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_SECONDARY_CONTAINER
import pl.dawidfendler.ui.theme.SEED
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_32
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_10
import pl.dawidfendler.ui.theme.sp_12
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_16
import pl.dawidfendler.ui.theme.sp_18
import pl.dawidfendler.ui.theme.sp_20
import pl.dawidfendler.ui.theme.sp_28
import pl.dawidfendler.util.ext.normalizeValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen(
    modifier: Modifier,
    state: CurrencyConverterState,
    onAction: (CurrencyConverterAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CurrencyCard(
                code = state.mainCurrencyCode,
                name = state.mainCurrencyFullName,
                flag = state.mainCurrencyFlag,
                isMainCurrency = true,
                currencies = state.currencies,
                onAction = onAction
            )

            IconButton(
                onClick = {
                    onAction(CurrencyConverterAction.SwitchCurrency)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MD_THEME_LIGHT_SECONDARY_CONTAINER.copy(alpha = 0.5f),
                    contentColor = SEED
                ),
                modifier = Modifier
                    .size(dp_48)
            ) {
                Icon(
                    Icons.Default.SwapHoriz,
                    contentDescription = null,
                    modifier = Modifier.size(dp_32)
                )
            }

            CurrencyCard(
                code = state.secondCurrencyCode,
                name = state.secondCurrencyFullName,
                flag = state.secondCurrencyFlag,
                isMainCurrency = false,
                currencies = state.currencies,
                onAction = onAction
            )
        }
        MainMoneyValueInput(
            value = state.mainCurrencyValue,
            flag = state.secondCurrencyFlag,
            onAction = onAction
        )
//        OutlinedTextField(
//            value = "100.00",
//            onValueChange = {},
//            label = null,
//            leadingIcon = { Text("$", fontSize = sp_20) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 24.dp),
//            textStyle = LocalTextStyle.current.copy(
//                fontSize = sp_24,
//                textAlign = TextAlign.Center
//            ),
//            singleLine = true
//        )

        ConvertedValue(
            value = state.secondCurrencyValue,
            code = state.secondCurrencyCode
        )

        Spacer(modifier = Modifier.height(24.dp))

        InfoFooter(state.exchangeRateDate)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCard(
    code: String,
    name: String,
    flag: String,
    isMainCurrency: Boolean = true,
    currencies: List<ExchangeRateTable>,
    onAction: (CurrencyConverterAction) -> Unit
) {
    var isCurrencyPickerShow by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (isCurrencyPickerShow) {
        ModalBottomSheet(
            onDismissRequest = {
                isCurrencyPickerShow = false
            },
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                }
            },
            sheetState = sheetState,
            content = {
                CurrencyPicker(
                    onCurrencySelected = {
                        isCurrencyPickerShow = false
                        onAction.invoke(
                            CurrencyConverterAction.ChangeCurrency(
                                isMainCurrency = isMainCurrency,
                                code = it
                            )
                        )
                    },
                    currencies = currencies
                )
            },
            modifier = Modifier.fillMaxHeight(0.95f),
            scrimColor = Color.White
        )
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(dp_24))
            .background(MD_THEME_LIGHT_SECONDARY_CONTAINER.copy(alpha = 0.5f))
            .clickable { isCurrencyPickerShow = true }
            .padding(
                horizontal = dp_20,
                vertical = dp_12
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(dp_32)
                .background(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = flag,
                fontSize = sp_16,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(dp_8))

        CustomText(
            text = code,
            fontSize = sp_18,
            fontWeight = FontWeight.Bold,
            letterSpacing = sp_0,
            color = Color.Black
        )

        Spacer(Modifier.height(dp_8))

        CustomText(
            text = name,
            fontSize = sp_12,
            color = Color.DarkGray.copy(alpha = 0.6f),
            fontWeight = FontWeight.Normal,
            letterSpacing = sp_0
        )
    }
}

@Composable
private fun MainMoneyValueInput(
    value: String,
    flag: String,
    onAction: (CurrencyConverterAction) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (isFocused) {
                onAction(
                    CurrencyConverterAction.ChangeCurrencyValue(
                        value = normalizeValue(value, it)
                    )
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(dp_16),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MD_THEME_LIGHT_OUTLINE.copy(alpha = 0.5f),
            unfocusedContainerColor = MD_THEME_LIGHT_OUTLINE_VARIANT.copy(alpha = 0.5f),
            focusedBorderColor = MD_THEME_LIGHT_OUTLINE,
            cursorColor = MD_THEME_LIGHT_OUTLINE,
        ),
        textStyle = TextStyle(
            color = if (isFocused) {
                Color.Black
            } else {
                MD_THEME_LIGHT_OUTLINE.copy(alpha = 0.5f)
            },
            textAlign = TextAlign.End,
            fontSize = sp_20,
            fontWeight = FontWeight.Bold
        ),
        leadingIcon = {
            CustomText(
                text = flag,
                fontSize = sp_20,
                fontWeight = FontWeight.Normal,
                color = if (isFocused) {
                    Color.Black
                } else {
                    MD_THEME_LIGHT_OUTLINE.copy(alpha = 0.5f)
                }
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    )
}

@Composable
private fun ConvertedValue(value: String, code: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MD_THEME_LIGHT_SECONDARY_CONTAINER.copy(0.5f),
                RoundedCornerShape(dp_24)
            )
            .padding(vertical = dp_24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(
            text = stringResource(R.string.converted_amount),
            fontSize = sp_14,
            color = SEED,
            fontWeight = FontWeight.Normal,
            letterSpacing = sp_0,
            modifier = Modifier.padding(bottom = dp_12)
        )
        CustomText(
            text = stringResource(
                R.string.special_mark_tylda,
                value,
                code
            ),
            fontSize = sp_28,
            letterSpacing = sp_0,
            color = Color.Black
        )
    }
}

@Composable
private fun InfoFooter(date: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Gray
            )
            Spacer(Modifier.width(dp_6))
            CustomText(
                text = stringResource(R.string.date_of_the_current_exchange_rates_title),
                textAlign = TextAlign.Start,
                letterSpacing = sp_0,
                color = Color.Gray,
                fontSize = sp_10,
                modifier = Modifier
            )

            CustomText(
                text = date,
                color = Color.Gray,
                fontSize = sp_10,
            )
        }
    }
}
