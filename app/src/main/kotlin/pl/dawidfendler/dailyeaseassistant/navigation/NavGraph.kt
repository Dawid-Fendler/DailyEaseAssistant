package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
        }
    }
}
