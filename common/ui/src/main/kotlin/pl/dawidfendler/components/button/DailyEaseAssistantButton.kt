package pl.dawidfendler.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_56
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.sp_14

@Composable
fun DailyEaseAssistantButton(
    name: String = "",
    onClick: () -> Unit = {},
    isLoading: Boolean = false
) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(dp_56)
            .padding(horizontal = dp_56),
        onClick = { onClick.invoke() },
        content = {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(dp_24)
                )
            } else {
                Text(
                    text = name,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = sp_14,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = dp_6
        ),
        shape = RoundedCornerShape(dp_16)
    )
}
