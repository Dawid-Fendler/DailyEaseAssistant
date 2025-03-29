package pl.dawidfendler.components.empty_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.ui.R
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.dp_80

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    showTryAgainText: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(
            modifier = Modifier.padding(vertical = dp_8),
            text = stringResource(R.string.something_went_wrong),
            textAlign = TextAlign.Center
        )

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier
                .size(dp_80)
        )

        if (showTryAgainText) {
            CustomText(
                modifier = Modifier.padding(vertical = dp_8),
                text = stringResource(R.string.try_again),
                textAlign = TextAlign.Center
            )
        }
    }
}
