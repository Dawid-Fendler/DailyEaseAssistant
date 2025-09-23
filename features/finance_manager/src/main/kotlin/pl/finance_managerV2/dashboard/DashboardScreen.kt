package pl.finance_managerV2.dashboard

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_28
import pl.dawidfendler.ui.theme.dp_8
import pl.finance_managerV2.components.accounts.AccountCarousel
import pl.finance_managerV2.components.add_account.AddAccountBottomSheet
import pl.finance_managerV2.components.quic_action.QuickActionSections
import pl.finance_managerV2.components.total_balance.TotalBalance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
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
                onAddExpense = {},
                onAddIncome = {},
                onConverter = {},
                onCharts = {}
            )

            AnimatedVisibility(state.addAccountBottomSheetVisibility) {
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
                        currencies = state.currencies
                    )
                }
            }
        }
    }
}