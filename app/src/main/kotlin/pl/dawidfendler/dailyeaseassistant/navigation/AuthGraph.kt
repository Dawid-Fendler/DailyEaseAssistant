package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import pl.dawidfendler.authentication.login.loginRoute
import pl.dawidfendler.authentication.registration.registrationRoute
import pl.dawidfendler.onboarding.navigation.onboardingRoute
import pl.dawidfendler.util.navigation.Destination

internal fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    startDestination: Destination
) {
    navigation<Destination.AuthGraph>(
        startDestination = startDestination
    ) {
        onboardingRoute {
            navController.popBackStack()
            navController.navigate(Destination.Login) {
                popUpTo(Destination.AuthGraph) {
                    inclusive = true
                }
            }
        }

        loginRoute(
            navigateToRegistration = {
                navController.navigate(Destination.Registration)
            },
            navigateToMainScreen = {
                navController.navigate(Destination.Home) {
                    popUpTo(Destination.AuthGraph) { inclusive = true }
                }
            }
        )

        registrationRoute(
            navigateBack = {
                navController.navigateUp()
            },
            navigateToLogin = {
                navController.popBackStack()
                navController.navigate(Destination.Login)
            },
            navigateToMain = {
                navController.navigate(Destination.Home) {
                    popUpTo(Destination.AuthGraph) { inclusive = true }
                }
            }
        )
    }
}
