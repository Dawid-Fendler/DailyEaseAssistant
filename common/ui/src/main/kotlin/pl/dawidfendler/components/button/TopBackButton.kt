package pl.dawidfendler.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24

@Composable
fun TopBackButton(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Icon(
            modifier = Modifier
                .padding(start = dp_16, top = dp_24)
                .clickable { onBackClick.invoke() },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
    }
}
