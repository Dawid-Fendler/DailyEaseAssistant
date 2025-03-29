package pl.dawidfendler.finance_manager.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.components.empty_screen.EmptyScreen
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.dp_0
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_400
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_16

@Composable
fun TransactionsHistoryBottomSheet(
    modifier: Modifier = Modifier,
    transactionsHistory: List<String>,
    isTransactionFetchError: Boolean
) {
    CustomText(
        text = stringResource(R.string.currencies_bottom_dialog_title),
        modifier = Modifier
            .padding(dp_16),
        textAlign = TextAlign.Center
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(dp_400),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    bottom = if (isTransactionFetchError) {
                        dp_16
                    } else {
                        dp_0
                    }
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(transactionsHistory) { transaction ->
                TransactionItem(
                    transaction = transaction
                )
            }
        }

        if (isTransactionFetchError) {
            EmptyScreen()
        }
    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dp_16,
                vertical = dp_4
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(dp_16)
            )
            .border(
                width = dp_1,
                color = Color.Black,
                shape = RoundedCornerShape(dp_16)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = transaction,
            color = Color.Black,
            style = TextStyle(
                fontSize = sp_16,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = dp_8)
                .weight(1f)
        )
    }
}
