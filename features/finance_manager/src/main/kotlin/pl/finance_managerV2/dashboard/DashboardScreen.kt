package pl.finance_managerV2.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_28
import pl.dawidfendler.ui.theme.dp_8
import pl.finance_managerV2.components.accounts.AccountCarousel
import pl.finance_managerV2.components.add_account.AddAccountBottomSheet
import pl.finance_managerV2.components.quic_action.QuickActionSections
import pl.finance_managerV2.components.total_balance.TotalBalance
import pl.finance_managerV2.model.AccountUiModel
import pl.finance_managerV2.transaction_operation.TransactionOperationBottomDialog
import pl.finance_managerV2.transaction_operation.categories.model.CategoryUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(dp_16),
                strokeWidth = dp_8
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccountCarousel(
                accounts = state.accounts,
                showAddAccountCard = state.showAddAccountCard,
                modifier = Modifier.padding(top = dp_16),
                onAddNewAccount = {
                    onAction(DashboardAction.ChangeAddAccountBottomSheetVisibility(true))
                }
            )

            Spacer(Modifier.height(dp_28))

            TotalBalance(
                modifier = Modifier,
                totalBalance = state.finalTotalBalance,
                accountsCurrencyCode = state.accountsCurrencyCode
            )

            Spacer(Modifier.height(dp_28))

            QuickActionSections(
                onAddExpense = {
                    onAction(
                        DashboardAction.ChangeTransactionOptionDialogVisibility(
                            isVisible = true,
                            isExpense = true
                        )
                    )
                },
                onAddIncome = {
                    onAction(
                        DashboardAction.ChangeTransactionOptionDialogVisibility(
                            isVisible = true,
                            isExpense = false
                        )
                    )
                },
                onConverter = {},
                onCharts = {}
            )
        }
        if (state.addAccountBottomSheetVisibility) {
            AddAccountDialog(
                onAction = onAction,
                currencies = state.currencies
            )
        }

        if (state.showTransactionOptionDialog) {
            TransactionOperationDialog(
                accounts = state.accounts,
                isExpense = state.isExpenseDialog,
                categories = if (state.isExpenseDialog) state.expenseCategories else state.incomeCategories,
                onAction = onAction
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionOperationDialog(
    accounts: List<AccountUiModel>,
    isExpense: Boolean,
    categories: List<CategoryUiModel>,
    onAction: (DashboardAction) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val onDismiss = { onAction(DashboardAction.ChangeTransactionOptionDialogVisibility(false)) }

    ModalBottomSheet(
        sheetState = sheetState,
        dragHandle = null,
        onDismissRequest = { onDismiss.invoke() }
    ) {
        TransactionOperationBottomDialog(
            isExpense = isExpense,
            accounts = accounts,
            onCancelClick = { onDismiss.invoke() },
            onSaveTransactionClick = { selectedAccount, date, amount, description, categoryName ->
                onAction(
                    DashboardAction.OnSaveTransactionClick(
                        selectedAccount = selectedAccount,
                        date = date,
                        amount = amount,
                        description = description,
                        categoryName = categoryName,
                        isExpense = isExpense
                    )
                )
                onDismiss.invoke()
            },
            categories = categories
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAccountDialog(
    onAction: (DashboardAction) -> Unit,
    currencies: List<ExchangeRateTable>
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onAction(DashboardAction.ChangeAddAccountBottomSheetVisibility(false))
        },
        dragHandle = null
    ) {
        AddAccountBottomSheet(
            onAddAccountClick = { name, code ->
                onAction.invoke(
                    DashboardAction.AddNewAccount(
                        accountName = name,
                        currencyCode = code
                    )
                )
            },
            currencies = currencies
        )
    }
}