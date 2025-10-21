package pl.finance_managerV2.transaction_operation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.domain.model.categories.CategoryType
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.MD_THEME_DARK_PRIMARY
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY_CONTAINER
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_20
import pl.finance_managerV2.model.AccountUiModel
import pl.finance_managerV2.transaction_operation.categories.components.CategoryDropdown
import pl.finance_managerV2.transaction_operation.categories.model.CategoryUiModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransactionOperationBottomDialog(
    modifier: Modifier = Modifier,
    isExpense: Boolean,
    accounts: List<AccountUiModel>,
    onCancelClick: () -> Unit,
    onSaveTransactionClick: () -> Unit,
    categories: List<CategoryUiModel>
) {

    val selectedAccount = remember { mutableStateOf<AccountUiModel?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val interactionSource = remember { MutableInteractionSource() }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(categories.first()) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dp_16)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dp_80)
        ) {
            IconButton(
                onClick = onCancelClick,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(dp_24)
                    )
                }
            )

            CustomText(
                text = if (isExpense) stringResource(R.string.add_expense) else stringResource(R.string.add_income),
                letterSpacing = sp_0,
                fontSize = sp_20,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

        }

        HorizontalDivider(
            thickness = dp_1,
            color = Color.Gray.copy(alpha = 0.4f),
            modifier = Modifier.padding(horizontal = dp_1)
        )

        Spacer(Modifier.height(dp_20))

        CustomText(
            text = stringResource(R.string.category),
            letterSpacing = sp_0,
            fontSize = sp_14,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(dp_8))

        CategoryDropdown(
            selected = selectedCategory,
            onCategorySelected = {
                selectedCategory = it
            },
            categories = categories,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(dp_20))

        CustomText(
            text = stringResource(R.string.account),
            letterSpacing = sp_0,
            fontSize = sp_14,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(dp_8))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(dp_8),
            verticalArrangement = Arrangement.spacedBy(dp_8),
            maxItemsInEachRow = 3
        ) {
            accounts.take(5).forEach { account ->
                AssistChip(
                    onClick = { selectedAccount.value = account },
                    label = {
                        CustomText(
                            text = account.mainName,
                            letterSpacing = sp_0,
                            fontSize = sp_14,
                            color = if (account.mainName == selectedAccount.value?.mainName) {
                                Color.White
                            } else {
                                Color.Black
                            },
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    leadingIcon = {
                        CustomText(
                            text = "🏦",
                            letterSpacing = sp_0,
                            fontSize = sp_14,
                            color = if (account.mainName == selectedAccount.value?.mainName) {
                                Color.White
                            } else {
                                Color.Black
                            },
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (account.mainName == selectedAccount.value?.mainName) {
                            MD_THEME_LIGHT_PRIMARY
                        } else {
                            MD_THEME_LIGHT_PRIMARY_CONTAINER
                        }
                    )
                )
            }
        }

        Spacer(Modifier.height(dp_20))

        OutlinedTextField(
            value = amount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { amount = it },
            label = {
                CustomText(
                    text = stringResource(R.string.amount),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            },
            leadingIcon = {
                CustomText(
                    text = stringResource(R.string.dolar_sign),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                CustomText(
                    text = selectedAccount.value?.currency ?: stringResource(R.string.usd),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
            },
            shape = RoundedCornerShape(dp_12),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(dp_20))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                CustomText(
                    text = stringResource(R.string.description),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            },
            shape = RoundedCornerShape(dp_12),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(dp_20))



        if (showDatePicker) {
            DatePicker(
                onDateSelected = { selectedDate = it },
                onDismiss = { showDatePicker = false }
            )
        }

        OutlinedTextField(
            value = selectedDate.toString(),
            onValueChange = {},
            label = {
                CustomText(
                    text = stringResource(R.string.date_time),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = null,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    showDatePicker = true
                },
            readOnly = true,
            interactionSource = interactionSource,
            enabled = false,
            shape = RoundedCornerShape(dp_12),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dp_12)
        ) {
            OutlinedButton(
                onClick = onCancelClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MD_THEME_DARK_PRIMARY
                ),
                modifier = Modifier.weight(1f)
            ) {
                CustomText(
                    text = stringResource(R.string.cancel),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = onSaveTransactionClick,
                modifier = Modifier.weight(1f)
            ) {
                CustomText(
                    text = stringResource(R.string.save_transaction),
                    letterSpacing = sp_0,
                    fontSize = sp_14,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val millis = datePickerState.selectedDateMillis
                    millis?.let {
                        val selected = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selected)
                    }
                    onDismiss()
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}