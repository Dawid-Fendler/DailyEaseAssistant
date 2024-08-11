package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.authentication.login.loginRoute
import pl.dawidfendler.authentication.registration.registrationRoute
import pl.dawidfendler.onboarding.navigation.onboardingRoute
import pl.dawidfendler.util.navigation.Navigation

@Composable
internal fun SetupNavGraph(
    startDestination: Navigation,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        onboardingRoute {
            navController.popBackStack()
            navController.navigate(Navigation.LoginNavigation)
        }

        loginRoute(
            navigateToRegistration = {
                navController.navigate(Navigation.RegistrationNavigation)
            },
            navigateToMainScreen = {
                navController.popBackStack()
            }
        )

        registrationRoute(
            navigateBack = {
                navController.navigateUp()
            },
            navigateToLogin = {
                navController.popBackStack()
                navController.navigate(Navigation.LoginNavigation)
            },
            navigateToMain = {}
        )
    }
}
