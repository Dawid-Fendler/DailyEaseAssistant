package pl.dawidfendler.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.dawidfendler.onboarding.util.wormTransition
import pl.dawidfendler.ui.theme.WORM_INDICATOR_SIZE
import pl.dawidfendler.ui.theme.WORM_INDICATOR_SPACING_SIZE

// Using https://blog.canopas.com/jetpack-compose-how-to-implement-custom-pager-indicators-8b6a01d63964
@Composable
internal fun WormIndicator(
    count: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    spacing: Dp = WORM_INDICATOR_SPACING_SIZE
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = modifier
                .height(WORM_INDICATOR_SIZE),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(count) {
                Box(
                    modifier = Modifier
                        .size(WORM_INDICATOR_SIZE)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
            }
        }

        Box(
            Modifier
                .wormTransition(pagerState)
                .size(15.dp)
        )
    }
}
