package pl.dawidfendler.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_36
import pl.dawidfendler.ui.theme.dp_6

@Composable
fun BorderIconButton(
    modifier: Modifier = Modifier,
    size: Dp = dp_36,
    borderShape: RoundedCornerShape = RoundedCornerShape(size = dp_20),
    borderColor: Color = Color.White,
    borderWidth: Dp = dp_1,
    containerPadding: Dp = dp_6,
    onClick: () -> Unit = {},
    rippleEffectRadius: Dp = dp_20,
    rippleEffectColor: Color = Color.White,
    icon: ImageVector,
    iconTint: Color = Color.White
) {
    Box(
        modifier = modifier
            .size(size)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = borderShape
            )
            .padding(containerPadding)
            .clickable(
                onClick = {
                    onClick.invoke()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = rippleEffectRadius,
                    color = rippleEffectColor
                )
            )
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconTint),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
