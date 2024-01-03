package pl.dawidfendler.dailyeaseassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.dawidfendler.onboarding.navigation.onboardingRoute

@Composable
fun SetupNavGraph(
    startDestination: String,
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
