package pl.dawidfendler.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.R
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_2
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_36
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.sp_36

@Composable
fun FinanceManagerWidget(
    modifier: Modifier = Modifier,
    navigateToFinanceManager: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = dp_16)
            .padding(top = dp_16)
            .clickable {
                navigateToFinanceManager.invoke()
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = dp_4
        ),
        shape = RoundedCornerShape(dp_24),
        border = BorderStroke(
            width = dp_2,
            color = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            CustomText(
                text = stringResource(R.string.account_balance_title),
                modifier = Modifier
                    .padding(
                        top = dp_16,
                        start = dp_16
                    )
            )

            CustomText(
                text = stringResource(
                    pl.dawidfendler.ui.R.string.account_balance_money_format,
                    "5402.20"
                ),
                fontSize = sp_36,
                color = Color.Black,
                modifier = Modifier
                    .padding(
                        top = dp_16,
                        start = dp_16
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dp_16, vertical = dp_16)
                    .background(
                        shape = RoundedCornerShape(dp_16),
                        color = MaterialTheme.colorScheme.primary
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.account_balance_income_title),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier
                            .padding(top = dp_4)
                    )
                    Text(
                        text = stringResource(R.string.account_balance_income_format, "420"),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(vertical = dp_2)
                    )
                }

                VerticalDivider(
                    modifier = Modifier
                        .height(dp_36)
                        .padding(vertical = dp_4),
                    thickness = dp_2,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.account_balance_spending_title),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier
                            .padding(top = dp_4)
                    )
                    Text(
                        text = stringResource(R.string.account_balance_spending_format, "3500"),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(vertical = dp_2)
                    )
                }
            }
        }
    }
}
