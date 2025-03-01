package pl.dawidfendler.components.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_36
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.sp_12

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    size: Dp = dp_36,
    shape: RoundedCornerShape = RoundedCornerShape(size = dp_20),
    color: Color = MaterialTheme.colorScheme.primary,
    containerPadding: Dp = dp_6,
    onClick: () -> Unit = {},
    rippleEffectRadius: Dp = dp_24,
    rippleEffectColor: Color = MaterialTheme.colorScheme.primary,
    icon: ImageVector,
    text: String = "",
    showText: Boolean = false,
    fontSize: TextUnit = sp_12,
    fontWeight: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily = FontFamily.Default,
    textColor: Color = Color.Black,
    imageTintColor: Color = Color.White
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(
                    shape = shape,
                    color = color
                )
                .padding(containerPadding)
                .clickable(
                    onClick = {
                        onClick.invoke()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        radius = rippleEffectRadius,
                        color = rippleEffectColor
                    )
                )

        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(imageTintColor),
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        AnimatedVisibility(showText) {
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
                style = TextStyle(
                    fontFamily = fontFamily,
                    color = textColor,
                ),
                modifier = Modifier
                    .padding(top = dp_6)
            )
        }
    }
}
