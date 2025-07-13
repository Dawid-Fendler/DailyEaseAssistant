@file:OptIn(ExperimentalMaterial3Api::class)

package pl.dawidfendler.finance_manager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.dawidfendler.account_balance.AccountBalance
import pl.dawidfendler.account_balance.bottom_sheet.AccountBalanceCurrenciesBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.ActionBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.ActionBottomSheet.AccountBalanceCurrenciesBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.ActionBottomSheet.AddMoneyBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.ActionBottomSheet.CurrenciesBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.ActionBottomSheet.SpentMoneyBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.ActionBottomSheet.TransactionHistoryBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.CurrenciesBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.TransactionOperationBottomSheet
import pl.dawidfendler.account_balance.bottom_sheet.TransactionOperations
import pl.dawidfendler.account_balance.bottom_sheet.TransactionsHistoryBottomSheet
import pl.dawidfendler.ui.theme.dp_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceManagerScreen(
    modifier: Modifier = Modifier,
    state: FinanceManagerState,
    onAction: (FinanceManagerAction) -> Unit,
    navigate: () -> Unit
) {
    onAction(FinanceManagerAction.GetInitialData)
    var isSheetOpen by rememberSaveable { mutableStateOf<ActionBottomSheet?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = isSheetOpen == CurrenciesBottomSheet ||
                isSheetOpen == AccountBalanceCurrenciesBottomSheet
    )

    AnimatedVisibility(!state.isLoading) {
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
                },
                accountBalanceItems = state.userCurrencies,
                showAccountBalanceCurrencies = {
                    navigate.invoke()
//                    isSheetOpen = AccountBalanceCurrenciesBottomSheet
                }
            )

            if (isSheetOpen != null) {
                ModalBottomSheet(
                    content = {
                        when (isSheetOpen) {
                            AddMoneyBottomSheet -> ShowTransactionOperationBottomSheet(
                                onAction = onAction,
                                changeSheetOpenValue = { isSheetOpen = it },
                                transactionOperations = TransactionOperations.ADD
                            )

                            CurrenciesBottomSheet -> ShowCurrenciesBottomSheet(
                                onAction = onAction,
                                changeSheetOpenValue = { isSheetOpen = it },
                                state = state
                            )

                            SpentMoneyBottomSheet -> ShowTransactionOperationBottomSheet(
                                onAction = onAction,
                                changeSheetOpenValue = { isSheetOpen = it },
                                transactionOperations = TransactionOperations.MINUS
                            )

                            TransactionHistoryBottomSheet -> ShowTransactionsHistoryBottomSheet(
                                onAction = onAction,
                                state = state
                            )

                            AccountBalanceCurrenciesBottomSheet ->
                                AccountBalanceCurrenciesBottomSheet(
                                    currencies = state.currencies,
                                    isCurrenciesFetchDataError = state.isCurrenciesFetchDataError,
                                    selectedCurrency = state.userCurrencies,
                                    onConfirmClick = { userCurrencies ->
                                        isSheetOpen = null
                                        onAction.invoke(
                                            FinanceManagerAction.UpdateUserSelectedCurrencies(
                                                userCurrencies
                                            )
                                        )
                                    }
                                )

                            null -> Unit
                        }
                    },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(dp_24),
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    onDismissRequest = {
                        isSheetOpen = null
                    },
                    dragHandle = null
                )
            }
        }
    }
}

@Composable
private fun ShowTransactionOperationBottomSheet(
    onAction: (FinanceManagerAction) -> Unit,
    changeSheetOpenValue: (ActionBottomSheet?) -> Unit,
    transactionOperations: TransactionOperations
) {
    TransactionOperationBottomSheet(
        transactionOperations = transactionOperations,
        moneyOperationOnClick = { money ->
            changeSheetOpenValue(null)
            if (transactionOperations == TransactionOperations.ADD) {
                onAction.invoke(FinanceManagerAction.AddMoney(money))
            } else {
                onAction.invoke(FinanceManagerAction.SpentMoney(money))
            }
        }
    )
}

@Composable
private fun ShowCurrenciesBottomSheet(
    onAction: (FinanceManagerAction) -> Unit,
    changeSheetOpenValue: (ActionBottomSheet?) -> Unit,
    state: FinanceManagerState
) {
    onAction.invoke(FinanceManagerAction.GetSelectedCurrencies)
    CurrenciesBottomSheet(
        currencies = state.currencies,
        isCurrenciesFetchDataError = state.isCurrenciesFetchDataError,
        selectedCurrency = state.selectedCurrency,
        onSelectCurrency = { currency ->
            changeSheetOpenValue(null)
            onAction.invoke(
                FinanceManagerAction.SavedSelectedCurrencies(
                    currency
                )
            )
        }
    )
}

@Composable
private fun ShowTransactionsHistoryBottomSheet(
    onAction: (FinanceManagerAction) -> Unit,
    state: FinanceManagerState
) {
    onAction.invoke(FinanceManagerAction.GetTransaction)
    TransactionsHistoryBottomSheet(
        transactionsHistory = state.transaction,
        isTransactionFetchError = state.isTransactionFetchDataError
    )
}