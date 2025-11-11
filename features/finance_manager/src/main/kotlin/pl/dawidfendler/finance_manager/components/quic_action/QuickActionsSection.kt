package pl.dawidfendler.finance_manager.components.quic_action

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.PieChartOutline
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.finance_manager.R
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_32
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_14

@Composable
fun QuickActionSections(
    modifier: Modifier = Modifier,
    onAddExpense: () -> Unit,
    onAddIncome: () -> Unit,
    onConverter: () -> Unit,
    onCharts: () -> Unit,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = dp_16)) {
        CustomText(
            text = stringResource(R.string.quick_actions),
            color = Color.Black,
            letterSpacing = sp_0,
            modifier = Modifier
                .padding(bottom = dp_12)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickActionItem(
                icon = Icons.AutoMirrored.Filled.ReceiptLong,
                label = stringResource(R.string.add_expense),
                onClick = onAddExpense
            )
            QuickActionItem(
                icon = Icons.AutoMirrored.Filled.TrendingUp,
                label = stringResource(R.string.add_income),
                onClick = onAddIncome
            )
        }

        Spacer(modifier = Modifier.height(dp_16))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickActionItem(
                icon = Icons.Default.SyncAlt,
                label = stringResource(R.string.converter),
                onClick = onConverter
            )
            QuickActionItem(
                icon = Icons.Default.PieChartOutline,
                label = stringResource(R.string.charts),
                onClick = onCharts
            )
        }
    }
}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(dp_8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            tint = MD_THEME_LIGHT_PRIMARY,
            contentDescription = label,
            modifier = Modifier.size(dp_32)
        )

        Spacer(modifier = Modifier.height(dp_6))

        CustomText(
            text = label,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = sp_14,
            letterSpacing = sp_0
        )
    }
}