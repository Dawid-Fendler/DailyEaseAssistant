package pl.finance_managerV2.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_28
import pl.finance_managerV2.components.QuickActionSections
import pl.finance_managerV2.components.TotalBalance
import pl.finance_managerV2.components.accounts.AccountCarousel
import pl.finance_managerV2.model.AccountUiModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccountCarousel(
            accounts = listOf(
                AccountUiModel(
                    name = "Main Account",
                    currency = "PLN",
                    balance = "4 280,42 zł",
                    lastTransactionBalance = "–17,50 zł",
                    lastTransactionName = "Żabka",
                    isExpense = true
                ),
                AccountUiModel(
                    name = "Savings",
                    currency = "USD",
                    balance = "12 400,00 $",
                    lastTransactionBalance = "+120,00 $",
                    lastTransactionName = "PayPal",
                    isExpense = false
                )
            ),
            showAddAccountCard = true,
            modifier = Modifier.padding(top = dp_16)
        )

        Spacer(Modifier.height(dp_28))

        TotalBalance(
            modifier = Modifier,
            totalBalance = "16 680,42 zł",
            accountsCurrencyCode = listOf("USD", "EUR", "PLN")
        )

        Spacer(Modifier.height(dp_28))

        QuickActionSections(
            onAddExpense = {},
            onAddIncome = {},
            onConverter = {},
            onCharts = {}

        )
    }
}