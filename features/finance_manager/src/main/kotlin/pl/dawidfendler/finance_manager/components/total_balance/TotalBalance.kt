package pl.dawidfendler.finance_manager.components.total_balance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_0
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_32
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_16
import pl.dawidfendler.ui.theme.sp_36

@Composable
fun TotalBalance(
    modifier: Modifier = Modifier,
    totalBalance: String,
    accountsCurrencyCode: List<String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dp_32, vertical = dp_16)
    ) {
        CustomText(
            text = stringResource(R.string.total_balance),
            color = Color.Black,
            fontSize = sp_16,
            fontWeight = FontWeight.Normal,
            letterSpacing = sp_0,
            modifier = Modifier.padding(bottom = dp_8)
        )

        CustomText(
            text = totalBalance,
            color = Color.Black,
            fontSize = sp_36,
            letterSpacing = sp_0,
            modifier = Modifier.padding(bottom = dp_8)
        )

        Row(
            modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(accountsCurrencyCode.size) {
                CustomText(
                    text = accountsCurrencyCode[it],
                    color = MD_THEME_LIGHT_PRIMARY,
                    fontSize = sp_16,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = sp_0,
                    modifier = Modifier
                        .padding(
                            end = if (it < accountsCurrencyCode.size - 1) dp_16 else dp_0,
                            start = if (it == 0) dp_16 else dp_0
                        )
                )
            }
        }
    }
}
