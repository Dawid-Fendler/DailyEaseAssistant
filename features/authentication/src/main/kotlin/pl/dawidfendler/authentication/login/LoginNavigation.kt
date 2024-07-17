package pl.dawidfendler.authentication.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.loginRoute(
    navigateToRegistration: () -> Unit,
    navigateToMainScreen: () -> Unit
) {
    composable<Navigation.LoginNavigation> {
        val viewModel: LoginViewModel = hiltViewModel()

        LoginScreen(
            onLoginClick = { navigateToMainScreen.invoke() },
            onRegisterClick = { navigateToRegistration.invoke() }
        )
    }
}