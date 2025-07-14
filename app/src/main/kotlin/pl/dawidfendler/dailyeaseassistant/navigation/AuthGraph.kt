package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.authentication.login.loginRoute
import pl.dawidfendler.authentication.registration.registrationRoute
import pl.dawidfendler.onboarding.navigation.onboardingRoute
import pl.dawidfendler.util.navigation.Destination

@Composable
internal fun AuthNavHost(
    startDestination: Destination,
    navController: NavHostController
) {
    if (startDestination == Destination.Login ||
        startDestination == Destination.Onboarding
    ) {
        NavHost(
            startDestination = startDestination,
            navController = navController
        ) {
            onboardingRoute {
                navController.popBackStack()
                navController.navigate(Destination.Login)
            }

            loginRoute(
                navigateToRegistration = {
                    navController.navigate(Destination.Registration)
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
                    navController.navigate(Destination.Login)
                },
                navigateToMain = {
                    navController.popBackStack()
                    navController.navigate(Destination.Home)
                }
            )
        }
    } else {
        MainNavGraph(navController = navController)
    }
}
