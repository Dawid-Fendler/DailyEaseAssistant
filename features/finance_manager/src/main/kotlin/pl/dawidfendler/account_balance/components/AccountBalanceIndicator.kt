package pl.dawidfendler.account_balance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_8

@Composable
internal fun AccountBalanceIndicator(
    pageIndex: Int,
    pages: Int = 3
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(dp_6),
        ) {
            repeat(pages) { index ->
                Box(
                    modifier = Modifier
                        .size(dp_8)
                        .background(
                            color = if (index == pageIndex) {
                                Color.White
                            } else {
                                Color.White.copy(alpha = 0.3f)
                            },
                            shape = CircleShape
                        )
                )
            }
        }
    }
}