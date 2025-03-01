package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.authentication.login.loginRoute
import pl.dawidfendler.authentication.registration.registrationRoute
import pl.dawidfendler.onboarding.navigation.onboardingRoute
import pl.dawidfendler.util.navigation.Navigation

@Composable
internal fun AuthNavHost(
    startDestination: Navigation,
    navController: NavHostController
) {
    if (startDestination == Navigation.LoginNavigation ||
        startDestination == Navigation.OnboardingNavigation
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
                navigateToMain = {
                    navController.popBackStack()
                    navController.navigate(Navigation.HomeNavigation)
                }
            )
        }
    } else {
        MainNavGraph(navController = navController)
    }
}
