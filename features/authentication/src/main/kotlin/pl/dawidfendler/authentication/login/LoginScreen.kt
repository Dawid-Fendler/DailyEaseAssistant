package pl.dawidfendler.authentication.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import pl.dawidfendler.components.button.DailyEaseAssistantButton
import pl.dawidfendler.components.button.GoogleLoginButton
import pl.dawidfendler.components.text_field.LoginTextField
import pl.dawidfendler.components.text_field.PasswordTextField
import pl.dawidfendler.ui.R
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_180
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_36
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_24

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
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

        LoginTextField(
            value = state.email,
            onValueChange = { login -> onAction.invoke(LoginAction.OnLoginUpdate(login = login)) },
            hintText = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_email_hint),
            leadingIcon = pl.dawidfendler.authentication.R.drawable.ic_person,
            isError = state.isError,
            errorMessage = if (state.errorMessage == 0) "" else stringResource(state.errorMessage)
        )

        Spacer(modifier = Modifier.height(dp_16))

        PasswordTextField(
            value = state.password,
            onValueChange = { password -> onAction(LoginAction.OnPasswordUpdate(password = password)) },
            hintText = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_password_hint),
            leadingIcon = pl.dawidfendler.authentication.R.drawable.ic_password,
            isPasswordError = state.isError,
            passwordErrorMessage = if (state.errorMessage == 0) "" else stringResource(state.errorMessage),
            isPasswordVisible = state.isPasswordVisible
        )

        Spacer(modifier = Modifier.height(dp_36))

        DailyEaseAssistantButton(
            name = stringResource(id = pl.dawidfendler.authentication.R.string.login_screen_login_button_title),
            onClick = { onAction(LoginAction.OnLoginClick) },
            isLoading = state.isLogin
        )

        Spacer(modifier = Modifier.height(dp_16))

        RegisterButton(onRegisterClick)

        Spacer(modifier = Modifier.height(dp_24))

        GoogleLoginButton {  }
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
