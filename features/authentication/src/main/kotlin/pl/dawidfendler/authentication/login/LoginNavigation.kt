package pl.dawidfendler.authentication.login

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.loginRoute(
    navigateToRegistration: () -> Unit,
    navigateToMainScreen: () -> Unit
) {
    composable<Navigation.LoginNavigation> {
        val viewModel: LoginViewModel = hiltViewModel()
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current
        ObserveAsEvents(flow = viewModel.eventChannel) { event ->
            when (event) {
                is LoginEvent.Error -> {
                    keyboardController?.hide()
                    Toast.makeText(
                        context,
                        event.error.asString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }

                LoginEvent.LoginSuccess -> {
                    viewModel.saveOnboardingDisplayed()
                    keyboardController?.hide()
                    navigateToMainScreen()
                }
            }
        }
        LoginScreen(
            state = viewModel.state,
            onAction = viewModel::onAction,
            onRegisterClick = { navigateToRegistration.invoke() }
        )
    }
}
