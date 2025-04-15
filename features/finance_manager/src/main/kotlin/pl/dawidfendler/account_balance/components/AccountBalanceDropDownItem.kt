package pl.dawidfendler.account_balance.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.ui.theme.BLUE_BRUSH
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.sp_14

@Composable
internal fun AccountBalanceDropDownItem(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                fontSize = sp_14,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                )
            )
        },
        onClick = { onClick.invoke() },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(dp_24)
                    .background(
                        shape = RoundedCornerShape(size = dp_20),
                        brush = BLUE_BRUSH
                    )
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )
}