package pl.dawidfendler.authentication.registration

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.authentication.R
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.registrationRoute(
    navigateToLogin: () -> Unit,
    navigateBack: () -> Unit,
    navigateToMain: () -> Unit
) {
    composable<Navigation.RegistrationNavigation> {
        val viewModel: RegistrationViewModel = hiltViewModel()
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current
        ObserveAsEvents(flow = viewModel.eventChannel) { event ->
            when (event) {
                is RegistrationEvent.Error -> {
                    keyboardController?.hide()
                    Toast.makeText(
                        context,
                        event.error.asString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }

                RegistrationEvent.RegistrationSuccess -> {
                    keyboardController?.hide()
                    Toast.makeText(
                        context,
                        R.string.registration_successful,
                        Toast.LENGTH_LONG
                    ).show()
                    navigateToLogin()
                }
                RegistrationEvent.LoginSuccess -> {
                    navigateToMain()
                }
            }
        }
        RegistrationScreen(
            state = viewModel.state,
            onAction = viewModel::onAction,
            onBackClick = { navigateBack.invoke() }
        )
    }
}
