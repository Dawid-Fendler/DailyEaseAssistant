package pl.finance_managerV2.components.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pl.dawidfendler.components.indicator.WormIndicator
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_32
import pl.finance_managerV2.model.AccountUiModel

@Composable
fun AccountCarousel(
    modifier: Modifier = Modifier,
    accounts: List<AccountUiModel>
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { accounts.size }
    )

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = dp_32),
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) { page ->
        AccountCardView(account = accounts[page])
    }

    Spacer(modifier = Modifier.height(dp_12))

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        WormIndicator(
            modifier = Modifier,
            count = 5,
            pagerState = pagerState
        )
    }
}