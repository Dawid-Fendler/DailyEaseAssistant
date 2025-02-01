package pl.dawidfendler.components.text_field

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import pl.dawidfendler.ui.R
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_8

@Composable
fun PasswordTextField(
    value: String = "",
    onValueChange: (String) -> Unit,
    hintText: String = "",
    @DrawableRes leadingIcon: Int,
    isPasswordError: Boolean = false,
    passwordErrorMessage: String = "",
    isPasswordVisible: Boolean = false
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
                color = MaterialTheme.colorScheme.outline
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
            IconButton(onClick = { onValueChange.invoke("") }) {
                Icon(
                    painter = if (isPasswordVisible) {
                        painterResource(id = R.drawable.ic_visibility)
                    } else {
                        painterResource(id = R.drawable.ic_visibility_off)
                    },
                    contentDescription = null
                )
            }
        },
        shape = RoundedCornerShape(dp_16),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.outline,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        isError = isPasswordError,
        supportingText = {
            if (isPasswordError) {
                Text(
                    text = passwordErrorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = dp_8)
                )
            }
        },
        visualTransformation = if (isPasswordError) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}
