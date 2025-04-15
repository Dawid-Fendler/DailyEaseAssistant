package pl.dawidfendler.account_balance.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.dp_24

@Composable
fun AccountBalanceDropdownMenu(
    menuExpanded: Boolean,
    changeMenuExpandedValue: (Boolean) -> Unit,
    onAddClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { changeMenuExpandedValue(false) },
        shape = RoundedCornerShape(dp_24)
    ) {
        AccountBalanceDropDownItem(
            onClick = {
                onAddClick.invoke()
                changeMenuExpandedValue(false)
            },
            icon = Icons.Default.Add,
            text = stringResource(R.string.add)
        )
        AccountBalanceDropDownItem(
            onClick = {
                onMinusClick.invoke()
                changeMenuExpandedValue(false)
            },
            icon = Icons.Default.Remove,
            text = stringResource(R.string.remove)
        )
        AccountBalanceDropDownItem(
            onClick = {
                onCurrenciesClick.invoke()
                changeMenuExpandedValue(false)
            },
            icon = Icons.Default.CurrencyExchange,
            text = stringResource(R.string.currency)
        )
        AccountBalanceDropDownItem(
            onClick = {
                onHistoryClick.invoke()
                changeMenuExpandedValue(false)
            },
            icon = Icons.Default.History,
            text = stringResource(R.string.history)
        )
    }
}