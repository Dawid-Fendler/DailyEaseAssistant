package pl.dawidfendler.components.text_field

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import pl.dawidfendler.ui.theme.sp_2
import pl.dawidfendler.ui.theme.sp_20

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = sp_20,
    font: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = sp_2,
    color: Color = MaterialTheme.colorScheme.outline,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        style = TextStyle(
            fontFamily = font,
            color = color,
            letterSpacing = letterSpacing,
        ),
        modifier = modifier
    )
}
