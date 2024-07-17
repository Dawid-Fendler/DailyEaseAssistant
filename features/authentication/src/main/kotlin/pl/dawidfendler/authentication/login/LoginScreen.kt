package pl.dawidfendler.authentication.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import pl.dawidfendler.ui.GoogleLoginButton
import pl.dawidfendler.ui.R
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_OUTLINE_VARIANT
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_180
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.dp_56
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_12
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_24

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LogoImage()

        Spacer(modifier = Modifier.height(dp_24))

        WelcomeText()

        Spacer(modifier = Modifier.height(dp_24))

        LoginField()

        Spacer(modifier = Modifier.height(dp_16))

        PasswordField()

        Spacer(modifier = Modifier.height(dp_48))

        LoginButton(onLoginClick)

        Spacer(modifier = Modifier.height(dp_16))

        RegisterButton(onRegisterClick)

        Spacer(modifier = Modifier.height(dp_24))

        GoogleLoginButton(onClicked = { onLoginClick.invoke() })
    }
}

@Composable
private fun LogoImage() {
    Image(
        modifier = Modifier
            .padding(top = dp_80)
            .size(dp_180),
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null
    )
}

@Composable
private fun WelcomeText() {
    val text = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_title)

    Text(
        text = prepareWelcomeText(text),
        style = TextStyle(
            fontSize = sp_24,
            textAlign = TextAlign.Center
        )
    )
}

private fun prepareWelcomeText(text: String): AnnotatedString {
    return buildAnnotatedString {
        val color = MD_THEME_LIGHT_PRIMARY
        val boldStyle = SpanStyle(color = color, fontWeight = FontWeight.Bold)
        val newText = text.substringAfter("to ")
        val oldText = text.substringBefore("D")
        append(oldText)
        withStyle(boldStyle) {
            append(newText)
        }
    }
}

@Composable
private fun LoginField() {
    var login by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
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
                painter = painterResource(id = pl.dawidfendler.authentication.R.drawable.ic_person),
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
        painterResource(id = pl.dawidfendler.authentication.R.drawable.ic_visibility)
    } else {
        painterResource(id = pl.dawidfendler.authentication.R.drawable.ic_visibility_off)
    }

    OutlinedTextField(
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
                painter = painterResource(id = pl.dawidfendler.authentication.R.drawable.ic_password),
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
private fun LoginButton(onLoginClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(dp_56)
            .padding(horizontal = dp_56),
        onClick = { onLoginClick.invoke() },
        content = {
            Text(
                text = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_login_button_title),
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
        text = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_create_account_title),
        color = MD_THEME_LIGHT_PRIMARY,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = sp_14
        ),
        textDecoration = TextDecoration.Underline
    )
}