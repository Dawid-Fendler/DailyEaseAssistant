package pl.dawidfendler.currency_converter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.currency_converter.components.CardWithArcShape
import pl.dawidfendler.currency_converter.components.CurrencyPicker
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.BLUE_BRUSH
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.SEED
import pl.dawidfendler.ui.theme.dp_0
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_14
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_200
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_28
import pl.dawidfendler.ui.theme.dp_30
import pl.dawidfendler.ui.theme.dp_32
import pl.dawidfendler.ui.theme.dp_36
import pl.dawidfendler.ui.theme.dp_40
import pl.dawidfendler.ui.theme.dp_46
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_10
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_16
import pl.dawidfendler.ui.theme.sp_20
import pl.dawidfendler.ui.theme.sp_24
import pl.dawidfendler.util.ext.normalizeValue
import java.math.BigDecimal

@Composable
fun CurrencyConverterScreen(
    onBackClick: () -> Unit,
    state: CurrencyConverterState,
    onAction: (CurrencyConverterAction) -> Unit,
    query: String,
    filteredCurrencies: List<ExchangeRateTable>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = BLUE_BRUSH
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dp_16)
        ) {
            IconButton(onClick = { onBackClick.invoke() }, content = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(dp_28)
                )
            })
            CustomText(
                text = stringResource(R.string.currency_converter_title),
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = sp_24,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(dp_48))

        CardWithSemiCircleCutout(
            cutoutAtTop = false,
            radius = dp_30,
            title = stringResource(R.string.currency_you_change_title),
            state = state,
            onAction = onAction,
            query = query,
            filteredCurrencies = filteredCurrencies
        )

        Button(
            onClick = { onAction(CurrencyConverterAction.SwitchCurrency) },
            shape = CircleShape,
            modifier = Modifier
                .size(dp_46)
                .offset(y = -dp_20),
            contentPadding = PaddingValues(dp_0),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Icon(
                Icons.Default.SwapVert,
                contentDescription = null,
                tint = MD_THEME_LIGHT_PRIMARY,
                modifier = Modifier.size(dp_36)
            )
        }

        CardWithSemiCircleCutout(
            cutoutAtTop = true,
            radius = dp_30,
            modifier = Modifier.offset(y = -dp_40),
            title = stringResource(R.string.currency_you_give_title),
            state = state,
            onAction = onAction,
            isMainCurrency = false,
            query = query,
            filteredCurrencies = filteredCurrencies
        )

        ExchangeRateDateSection(date = state.exchangeRateDate)

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = dp_24)
                .padding(horizontal = dp_16),
            thickness = dp_1,
            color = Color.LightGray.copy(alpha = 0.5f)
        )

        DataSourceSection()

        HorizontalDivider(
            modifier = Modifier
                .padding(top = dp_24)
                .padding(horizontal = dp_16),
            thickness = dp_1,
            color = Color.LightGray.copy(alpha = 0.5f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithSemiCircleCutout(
    cutoutAtTop: Boolean,
    radius: Dp,
    modifier: Modifier = Modifier,
    title: String,
    state: CurrencyConverterState,
    onAction: (CurrencyConverterAction) -> Unit,
    isMainCurrency: Boolean = true,
    query: String = "",
    filteredCurrencies: List<ExchangeRateTable> = emptyList()
) {

    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
//        confirmValueChange = { it != SheetValue.Hidden }
    )
    val scope = rememberCoroutineScope()

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    isSheetOpen = false
                    sheetState.hide()
                }
            },
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = SEED),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                }
            },
            sheetState = sheetState,
            content = {
                CurrencyPicker(
                    query = query,
                    filteredCurrencies = filteredCurrencies,
                    onCurrencySelected = {
                        scope.launch {
                            isSheetOpen = false
                            sheetState.hide()
                        }
                        onAction.invoke(
                            CurrencyConverterAction.ChangeCurrency(
                                isMainCurrency = isMainCurrency,
                                code = it
                            )
                        )
                    },
                    onAction = onAction
                )
            },
            modifier = Modifier.fillMaxHeight(0.95f)
        )
    } else {
        Spacer(modifier = Modifier.height(dp_1))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(dp_200)
            .padding(horizontal = dp_16)
            .clickable {
                scope.launch {
                    isSheetOpen = true
                    sheetState.show()
                }
            },
        shape = CardWithArcShape(with(LocalDensity.current) { radius.toPx() }, cutoutAtTop),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(defaultElevation = dp_6)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TitleSection(title)

            CurrencySection(
                onAction = onAction,
                mainCurrencyCode = state.mainCurrencyCode,
                mainCurrencyFlag = state.mainCurrencyFlag,
                mainCurrencyFullName = state.mainCurrencyFullName,
                secondCurrencyCode = state.secondCurrencyCode,
                secondCurrencyFlag = state.secondCurrencyFlag,
                secondCurrencyFullName = state.secondCurrencyFullName,
                isMainCurrency = isMainCurrency,
                mainMoneyValue = state.mainCurrencyValue,
                secondMoneyValue = state.secondCurrencyValue
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(top = dp_24)
                    .padding(horizontal = dp_16),
                thickness = dp_1,
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            InfoSection(
                isMainCurrency = isMainCurrency,
                mainCurrencyMidValue = state.mainCurrencyMidValue,
                secondCurrencyMidValue = state.secondCurrencyMidValue
            )
        }
    }
}

@Composable
private fun TitleSection(title: String) {
    Row(modifier = Modifier.padding(top = dp_24, start = dp_16)) {
        CustomText(
            text = title, textAlign = TextAlign.Start, color = Color.White, fontSize = sp_14
        )
    }
}

@Composable
private fun CurrencySection(
    onAction: (CurrencyConverterAction) -> Unit,
    mainCurrencyCode: String,
    mainCurrencyFlag: String,
    mainCurrencyFullName: String,
    secondCurrencyCode: String,
    secondCurrencyFlag: String,
    secondCurrencyFullName: String,
    isMainCurrency: Boolean,
    mainMoneyValue: String,
    secondMoneyValue: String
) {

    Row(
        modifier = Modifier
            .padding(
                top = dp_14, start = dp_16
            )
    ) {
        Column {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(dp_32)
                        .background(
                            color = Color.LightGray.copy(alpha = 0.8f), shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isMainCurrency) {
                            mainCurrencyFlag
                        } else {
                            secondCurrencyFlag
                        }, fontSize = sp_16, textAlign = TextAlign.Center
                    )

                }

                CustomText(
                    text = if (isMainCurrency) {
                        mainCurrencyCode
                    } else {
                        secondCurrencyCode
                    },
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = sp_16,
                    modifier = Modifier.padding(start = dp_12)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = dp_8)
                    .height(dp_32)
                    .background(
                        brush = BLUE_BRUSH, shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(horizontal = dp_16), contentAlignment = Alignment.Center
            ) {
                CustomText(
                    text = if (isMainCurrency) {
                        mainCurrencyFullName
                    } else {
                        secondCurrencyFullName
                    },
                    letterSpacing = sp_0,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = sp_10,
                )
            }
        }

        Spacer(Modifier.weight(0.8f))

        Row(
            modifier = Modifier
                .padding(
                    top = dp_12,
                    end = dp_16
                )
                .weight(1f)
        ) {

            if (isMainCurrency) {
                MainMoneyValueInput(
                    value = mainMoneyValue,
                    onAction = onAction
                )
            } else {
                SecondValueInput(
                    value = secondMoneyValue,
                    onAction = onAction
                )
            }

        }
    }
}

@Composable
private fun MainMoneyValueInput(
    value: String,
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
                        value = normalizeValue(value, it), isMainCurrency = true
                    )
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(dp_16),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedContainerColor = Color.Transparent,
            unfocusedTrailingIconColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Transparent,
            focusedLeadingIconColor = Color.Transparent,
            focusedTrailingIconColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            cursorColor = MaterialTheme.colorScheme.outlineVariant,
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.outlineVariant,
            textAlign = TextAlign.End,
            fontSize = sp_20,
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        interactionSource = interactionSource
    )
}

@Composable
private fun SecondValueInput(
    value: String,
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
                        value = normalizeValue(value, it), isMainCurrency = false
                    )
                )
            }

        },
        singleLine = true,
        shape = RoundedCornerShape(dp_16),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedContainerColor = Color.Transparent,
            unfocusedTrailingIconColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Transparent,
            focusedLeadingIconColor = Color.Transparent,
            focusedTrailingIconColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            cursorColor = MaterialTheme.colorScheme.outlineVariant,
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.outlineVariant,
            textAlign = TextAlign.End,
            fontSize = sp_20,
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        interactionSource = interactionSource
    )
}

@Composable
private fun InfoSection(
    isMainCurrency: Boolean, mainCurrencyMidValue: BigDecimal, secondCurrencyMidValue: BigDecimal
) {
    Row(
        modifier = Modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText(
            text = stringResource(R.string.currency_current_rate_title),
            color = Color.White,
            fontSize = sp_16,
            modifier = Modifier.padding(
                top = dp_12, start = dp_16
            )
        )

        Spacer(Modifier.weight(0.8f))

        CustomText(
            text = if (isMainCurrency) {
                mainCurrencyMidValue.toString()
            } else {
                secondCurrencyMidValue.toString()
            }, color = Color.White, fontSize = sp_16, modifier = Modifier.padding(
                top = dp_12, end = dp_16
            )
        )
    }
}

@Composable
private fun ExchangeRateDateSection(
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp_16),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CustomText(
            text = stringResource(R.string.date_of_the_current_exchange_rates_title),
            textAlign = TextAlign.Start,
            letterSpacing = sp_0,
            color = Color.White,
            fontSize = sp_14,
            modifier = Modifier.weight(1f)
        )

        CustomText(
            text = date,
            color = Color.White,
            fontSize = sp_14,
        )
    }
}

@Composable
private fun DataSourceSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp_16),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CustomText(
            text = stringResource(R.string.currency_data_source_title),
            textAlign = TextAlign.Start,
            letterSpacing = sp_0,
            color = Color.White,
            fontSize = sp_14,
            modifier = Modifier.weight(1f)
        )

        CustomText(
            text = stringResource(R.string.data_source_value),
            color = Color.White,
            fontSize = sp_14,
        )
    }
}