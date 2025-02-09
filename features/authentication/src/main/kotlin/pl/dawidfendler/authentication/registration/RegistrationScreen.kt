package pl.dawidfendler.authentication.registration

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import pl.dawidfendler.authentication.R
import pl.dawidfendler.components.button.DailyEaseAssistantButton
import pl.dawidfendler.components.button.GoogleLoginButton
import pl.dawidfendler.components.button.TopBackButton
import pl.dawidfendler.components.common.ScreenTitle
import pl.dawidfendler.components.text_field.LoginTextField
import pl.dawidfendler.components.text_field.PasswordTextField
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.dp_56
import pl.dawidfendler.ui.theme.sp_14

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onAction: (RegistrationAction) -> Unit,
    onBackClick: () -> Unit
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(pl.dawidfendler.authentication.R.string.default_web_client_id))
        .requestEmail()
        .build()
    val mGoogleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)
    val googleResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val signInAccount = accountTask.getResult(ApiException::class.java)
                val authCredentials = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(authCredentials)
                    .addOnCompleteListener {
                        if (accountTask.isSuccessful) {
                            onAction.invoke(
                                RegistrationAction.OnGoogleLoginClick(
                                    signInAccount.idToken ?: ""
                                )
                            )
                        } else {
                            onAction.invoke(RegistrationAction.OnGoogleLoginError)
                        }
                    }
            } catch (e: Exception) {
                Log.e("RegistrationScreen Exception", "$e")
                onAction.invoke(RegistrationAction.OnGoogleLoginError)
            }
        }
    }

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

        ScreenTitle(title = stringResource(id = R.string.login_screen_create_account_title))

        Spacer(modifier = Modifier.height(dp_24))

        LoginTextField(
            value = state.email,
            onValueChange = { login -> onAction(RegistrationAction.OnLoginUpdate(login = login)) },
            hintText = stringResource(id = R.string.login_screen_email_hint),
            leadingIcon = R.drawable.ic_person,
            isError = state.isEmailValid,
            errorMessage = state.emailErrorMessage
        )

        Spacer(modifier = Modifier.height(dp_16))

        PasswordTextField(
            value = state.password,
            onValueChange = { password -> onAction(RegistrationAction.OnPasswordUpdate(password = password)) },
            hintText = stringResource(id = R.string.login_screen_password_hint),
            leadingIcon = R.drawable.ic_password,
            isPasswordError = state.isPasswordValid,
            passwordErrorMessage = state.passwordErrorMessage,
            isPasswordVisible = state.isPasswordVisible
        )

        Spacer(modifier = Modifier.height(dp_48))

        DailyEaseAssistantButton(
            name = stringResource(id = R.string.login_screen_sign_up_button_title),
            onClick = { onAction(RegistrationAction.OnRegisterClick) },
            isLoading = state.isRegistering
        )

        Spacer(modifier = Modifier.height(dp_24))

        RegisterButton(
            onLoginNavigation = {
                onBackClick.invoke()
            }
        )

        Spacer(modifier = Modifier.height(dp_48))

        GoogleLoginButton(
            onClicked = {
                googleResultLauncher.launch(mGoogleSignInClient.signInIntent)
            }
        )
    }
}

@Composable
private fun RegisterButton(onLoginNavigation: () -> Unit = {}) {
    Text(
        modifier = Modifier
            .clickable { onLoginNavigation.invoke() },
        text = prepareWelcomeText(stringResource(id = R.string.login_screen_already_have_account)),
        style = TextStyle(fontSize = sp_14)
    )
}

private fun prepareWelcomeText(text: String): AnnotatedString {
    return buildAnnotatedString {
        val color = MD_THEME_LIGHT_PRIMARY
        val boldStyle = SpanStyle(
            color = color,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        val newText = text.substringAfter("?")
        val oldText = text.substringBefore(" L")
        append(oldText)
        withStyle(boldStyle) {
            append(newText)
        }
    }
}
