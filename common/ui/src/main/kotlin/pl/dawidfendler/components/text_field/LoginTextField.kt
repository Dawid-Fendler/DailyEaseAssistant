package pl.dawidfendler.components.text_field

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE_VARIANT
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24

@Composable
fun LoginTextField(
    value: String = "",
    onValueChange: (String) -> Unit,
    hintText: String,
    @DrawableRes leadingIcon: Int,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp_24),
        value = value,
        label = {
            Text(
                text = hintText,
                fontWeight = FontWeight.Bold,
                color = MD_THEME_LIGHT_OUTLINE
            )
        },
        onValueChange = { onValueChange.invoke(it) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onValueChange.invoke("") }
            )
        },
        shape = RoundedCornerShape(dp_16),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MD_THEME_LIGHT_OUTLINE_VARIANT,
            unfocusedContainerColor = MD_THEME_LIGHT_OUTLINE_VARIANT,
            unfocusedTrailingIconColor = MD_THEME_LIGHT_OUTLINE,
            unfocusedLeadingIconColor = MD_THEME_LIGHT_OUTLINE,
            focusedLeadingIconColor = MD_THEME_LIGHT_PRIMARY,
            focusedTrailingIconColor = MD_THEME_LIGHT_PRIMARY
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = dp_16)
                )
            }
        }
    )
}
