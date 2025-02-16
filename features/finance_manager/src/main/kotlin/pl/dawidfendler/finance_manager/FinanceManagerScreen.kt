@file:OptIn(ExperimentalMaterial3Api::class)

package pl.dawidfendler.finance_manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pl.dawidfendler.FinanceManagerState
import pl.dawidfendler.finance_manager.components.AccountBalance
import pl.dawidfendler.finance_manager.components.bottom_sheet.ActionBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.ActionBottomSheet.AddMoneyBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.ActionBottomSheet.CurrenciesBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.ActionBottomSheet.SpentMoneyBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.ActionBottomSheet.TransactionHistoryBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.CurrenciesBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.TransactionOperationBottomSheet
import pl.dawidfendler.finance_manager.components.bottom_sheet.TransactionOperations
import pl.dawidfendler.finance_manager.components.bottom_sheet.TransactionsHistoryBottomSheet
import pl.dawidfendler.ui.theme.dp_24

@Composable
fun FinanceManagerScreen(
    modifier: Modifier = Modifier,
    state: FinanceManagerState,
    onAction: (FinanceManagerAction) -> Unit
) {
    var isSheetOpen by rememberSaveable { mutableStateOf<ActionBottomSheet?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = isSheetOpen == CurrenciesBottomSheet
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AccountBalance(
            onAddClick = {
                isSheetOpen = AddMoneyBottomSheet
            },
            onMinusClick = {
                isSheetOpen = SpentMoneyBottomSheet
            },
            onHistoryClick = {
                isSheetOpen = TransactionHistoryBottomSheet
            },
            onCurrenciesClick = {
                isSheetOpen = CurrenciesBottomSheet
            }
        )

        if (isSheetOpen != null) {
            ModalBottomSheet(
                content = {
                    when (isSheetOpen) {
                        AddMoneyBottomSheet -> TransactionOperationBottomSheet(
                            transactionOperations = TransactionOperations.ADD,
                            moneyOperationOnClick = { money ->
                                isSheetOpen = null
                                onAction.invoke(FinanceManagerAction.AddMoney(money))
                            }
                        )
                        CurrenciesBottomSheet -> {
                            onAction.invoke(FinanceManagerAction.GetSelectedCurrencies)
                            CurrenciesBottomSheet(
                                currencies = state.currencies,
                                selectedCurrency = state.selectedCurrencies,
                                onSelectCurrency = { currency ->
                                    isSheetOpen = null
                                    onAction.invoke(FinanceManagerAction.SavedSelectedCurrencies(currency))
                                }
                            )
                        }

                        SpentMoneyBottomSheet -> TransactionOperationBottomSheet(
                            transactionOperations = TransactionOperations.MINUS,
                            moneyOperationOnClick = { money ->
                                isSheetOpen = null
                                onAction.invoke(FinanceManagerAction.SpentMoney(money))
                            }
                        )
                        TransactionHistoryBottomSheet -> TransactionsHistoryBottomSheet(
                            transactionsHistory = emptyList()
                        )

                        null -> Unit
                    }
                },
                sheetState = sheetState,
                shape = RoundedCornerShape(dp_24),
                containerColor = Color.White,
                onDismissRequest = {
                    isSheetOpen = null
                },
                dragHandle = null
            )
        }
    }
}