package pl.dawidfendler.authentication.registration

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import pl.dawidfendler.authentication.R
import pl.dawidfendler.ui.GoogleLoginButton
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE_VARIANT
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.dp_56
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_12
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_36

@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    BackHandler {
        onBackClick.invoke()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopBackButton(onBackClick)

        Spacer(modifier = Modifier.height(dp_56))

        RegistrationTitle()

        Spacer(modifier = Modifier.height(dp_24))

        LoginField()

        Spacer(modifier = Modifier.height(dp_16))

        PasswordField()

        Spacer(modifier = Modifier.height(dp_48))

        SignUpButton(onRegisterClick)

        Spacer(modifier = Modifier.height(dp_24))

        RegisterButton(onRegisterClick)

        Spacer(modifier = Modifier.height(dp_48))

        GoogleLoginButton(onClicked = { onRegisterClick.invoke() })
    }
}

@Composable
private fun TopBackButton(onBackClick: () -> Unit) {
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

@Composable
private fun RegistrationTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = dp_24)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_create_account_title),
            style = TextStyle(
                fontSize = sp_36,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun LoginField() {
    var login by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp_24),
        value = login,
        label = {
            Text(
                text = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_email_hint),
                fontWeight = FontWeight.Bold,
                color = MD_THEME_LIGHT_OUTLINE
            )
        },
        onValueChange = { login = it },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        login = TextFieldValue("")
                    }
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
        )
    )
}

@Composable
private fun PasswordField() {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.ic_visibility)
    } else {
        painterResource(id = R.drawable.ic_visibility_off)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp_24),
        value = password,
        label = {
            Text(
                text = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_password_hint),
                fontWeight = FontWeight.Bold,
                color = MD_THEME_LIGHT_OUTLINE
            )
        },
        onValueChange = { password = it },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = null
                )
            }
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
private fun SignUpButton(onLoginClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(dp_56)
            .padding(horizontal = dp_56),
        onClick = { onLoginClick.invoke() },
        content = {
            Text(
                text = stringResource(id = R.string.login_screen_sign_up_button_title),
                color = Color.White,
                style = TextStyle(
                    fontSize = sp_14,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MD_THEME_LIGHT_PRIMARY
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = dp_6
        ),
        shape = RoundedCornerShape(dp_16)
    )
}

@Composable
private fun RegisterButton(onRegisterClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable { onRegisterClick.invoke() },
        text = prepareWelcomeText(stringResource(id = R.string.login_screen_already_have_account)),
        style = TextStyle(fontSize = sp_14)
    )
}

private fun prepareWelcomeText(text: String): AnnotatedString {
    return buildAnnotatedString {
        val color = MD_THEME_LIGHT_PRIMARY
        val boldStyle = SpanStyle(color = color, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)
        val newText = text.substringAfter("?")
        val oldText = text.substringBefore(" L")
        append(oldText)
        withStyle(boldStyle) {
            append(newText)
        }
    }
}