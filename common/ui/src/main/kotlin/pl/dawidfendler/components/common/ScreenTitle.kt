package pl.dawidfendler.components.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.sp_36

@Composable
fun ScreenTitle(
    title: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = dp_24)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = sp_36,
                textAlign = TextAlign.Center
            )
        )
    }
}
