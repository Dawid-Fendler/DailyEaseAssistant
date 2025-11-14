package pl.dawidfendler.finance_manager.currency_converter.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CardWithArcShape(
    private val radius: Float,
    private val cutoutAtTop: Boolean = false
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, size.width, size.height),
                        cornerRadius = CornerRadius(80f, 80f)
                    )
                )

                val centerX = size.width / 2
                val offsetY = if (cutoutAtTop) {
                    0f
                } else {
                    size.height
                }
                val arcStartAngle = if (cutoutAtTop) {
                    0f
                } else {
                    180f
                }
                val arcSweepAngle = 180f

                moveTo(centerX + radius, offsetY)

                arcTo(
                    rect = Rect(
                        left = centerX - radius,
                        top = offsetY - radius,
                        right = centerX + radius,
                        bottom = offsetY + radius
                    ),
                    startAngleDegrees = arcStartAngle,
                    sweepAngleDegrees = arcSweepAngle,
                    forceMoveTo = false
                )
                close()

                fillType = PathFillType.EvenOdd
            }
        )
    }
}
