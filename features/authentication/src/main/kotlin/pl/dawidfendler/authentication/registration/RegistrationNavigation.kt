package pl.dawidfendler.authentication.registration

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.registrationRoute(
    navigateToLogin: () -> Unit,
    navigateBack: () -> Unit
) {
    composable<Navigation.RegistrationNavigation> {
        val viewModel: RegistrationViewModel = hiltViewModel()

        RegistrationScreen(
            onBackClick = { navigateBack.invoke() },
            onRegisterClick = { navigateToLogin.invoke() }
        )
    }
}