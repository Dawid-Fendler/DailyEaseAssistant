package pl.dawidfendler.dailyeaseassistant.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.R
import pl.dawidfendler.components.button.BorderIconButton
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_14

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    name: String = "User",
    image: String = "",
    onUserIconClick: () -> Unit = {},
    onMenuItemClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = Color.Transparent
        ),
        title = {
            Row(
                modifier = Modifier
                    .padding(end = dp_16)
                    .height(dp_48),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BorderIconButton(
                    icon = Icons.Default.Face,
                    onClick = onUserIconClick
                )

                Spacer(modifier = Modifier.width(dp_16))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomText(
                        text = name,
                        letterSpacing = sp_0,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    CustomText(
                        text = stringResource(R.string.home_welcome_text),
                        fontSize = sp_14,
                        letterSpacing = sp_0,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
                BorderIconButton(
                    icon = Icons.Default.GridView,
                    onClick = onMenuItemClick
                )
            }
        }
    )
}
