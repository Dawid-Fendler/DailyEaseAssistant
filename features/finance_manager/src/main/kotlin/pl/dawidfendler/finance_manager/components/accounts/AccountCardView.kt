package pl.dawidfendler.finance_manager.components.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.model.AccountUiModel
import pl.dawidfendler.ui.theme.GREEN
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_200
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_4
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_16
import pl.dawidfendler.ui.theme.sp_36

@Composable
fun AccountCardView(
    account: AccountUiModel?,
    modifier: Modifier = Modifier,
    showAddAccountCard: Boolean = false,
    onAction: () -> Unit = {},
    isLastCard: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(dp_200)
            .padding(horizontal = dp_16),
        shape = RoundedCornerShape(dp_24),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = dp_4)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (showAddAccountCard && isLastCard) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onAction.invoke() }
                        .background(Color.LightGray.copy(alpha = 0.5f))
                ) {
                    IconButton(
                        onClick = { onAction.invoke() },
                        modifier = Modifier.align(Alignment.Center),
                        content = {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null,
                                tint = MD_THEME_LIGHT_PRIMARY,
                                modifier = Modifier.size(dp_48)
                            )
                        }
                    )
                }
            } else {
                CustomText(
                    text = "${account!!.mainName}: ${account.name} – ${account.currency}",
                    modifier = Modifier.padding(top = dp_16, start = dp_16),
                    color = Color.Black,
                    fontSize = sp_14,
                    letterSpacing = sp_0
                )

                Spacer(modifier = Modifier.weight(0.1f))

                CustomText(
                    text = account.balance,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dp_16),
                    color = Color.Black,
                    fontSize = sp_36,
                    letterSpacing = sp_0,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(0.1f))

                if (account.lastTransactionName != null && account.lastTransactionBalance != null) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = dp_16, start = dp_16),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomText(
                            text = account.lastTransactionBalance,
                            modifier = Modifier,
                            color = if (account.isExpense) Color.Red else GREEN,
                            fontSize = sp_16,
                            letterSpacing = sp_0
                        )
                        Spacer(modifier = Modifier.width(dp_4))
                        CustomText(
                            text = account.lastTransactionName,
                            modifier = Modifier,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = sp_14,
                            letterSpacing = sp_0
                        )
                    }
                }
            }
        }
    }
}
