package pl.dawidfendler.account_balance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.dawidfendler.account_balance.components.AccountBalanceItem
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_4

@Composable
fun AccountBalance(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCurrenciesClick: () -> Unit,
    onHistoryClick: () -> Unit,
    accountBalanceItems: List<UserCurrencies>,
    showAccountBalanceCurrencies: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) { accountBalanceItems.size }

    Card(
        modifier = modifier
            .widthIn(max = 300.dp)
            .height(200.dp)
            .padding(horizontal = dp_16)
            .padding(top = dp_16)
            .clickable {
                showAccountBalanceCurrencies()
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = dp_4
        ),
        shape = RoundedCornerShape(dp_24)
    ) {

        HorizontalPager(state = pagerState) { page ->
            AccountBalanceItem(
                userCurrencies = accountBalanceItems[page],
                onAddClick = onAddClick,
                onMinusClick = onMinusClick,
                onCurrenciesClick = onCurrenciesClick,
                onHistoryClick = onHistoryClick,
                pages = accountBalanceItems.size,
                pageIndex = page
            )
        }
    }
}