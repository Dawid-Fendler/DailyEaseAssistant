package pl.dawidfendler.account_balance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.account_balance.UserCurrencies
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.BLUE_BRUSH
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_16
import pl.dawidfendler.ui.theme.sp_20
import pl.dawidfendler.ui.theme.sp_36

@Composable
internal fun AccountBalanceItem(
    userCurrencies: UserCurrencies,
    onAddClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
    onHistoryClick: () -> Unit,
    pages: Int,
    pageIndex: Int
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(brush = BLUE_BRUSH)
    ) {
        Row(
            modifier = Modifier
                .padding(top = dp_16)
                .height(dp_24)
        ) {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    Icons.Default.MoreVert,
                    tint = Color.White,
                    contentDescription = null
                )
            }

            AccountBalanceDropdownMenu(
                menuExpanded = menuExpanded,
                changeMenuExpandedValue = { menuExpanded = it },
                onAddClick,
                onMinusClick,
                onCurrenciesClick,
                onHistoryClick
            )
        }
        CustomText(
            text = stringResource(R.string.account_name_title, userCurrencies.currencyName),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dp_16,
                    start = dp_16,
                    end = dp_16
                ),
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.8f),
            fontWeight = FontWeight.Normal,
            fontSize = sp_16
        )

        CustomText(
            text = userCurrencies.accountBalance,
            fontSize = if (userCurrencies.accountBalance.length > 9) {
                sp_20
            } else {
                sp_36
            },
            color = if (userCurrencies.isDebt) {
                Color.Red
            } else {
                Color.White
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dp_16,
                    end = dp_16,
                    top = dp_8
                ),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(dp_24))

        AccountBalanceIndicator(
            pageIndex = pageIndex,
            pages = pages
        )
    }
}