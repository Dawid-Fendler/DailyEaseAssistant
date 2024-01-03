package pl.dawidfendler.onboarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.onboarding.OnboardingScreen
import pl.dawidfendler.onboarding.OnboardingViewModel
import pl.dawidfendler.onboarding.navigation.OnboardingNavigationRoute.ONBOARDING_SCREEN_ROUTE

fun NavGraphBuilder.onboardingRoute(
    navigateToAuth: () -> Unit
) {
    composable(route = ONBOARDING_SCREEN_ROUTE) {
        val viewModel: OnboardingViewModel = hiltViewModel()

        OnboardingScreen(
            onFinishButtonClick = {
                viewModel.saveOnboardingDisplayed()
            },
            navigateToAuth = navigateToAuth
        )
    }
}

internal object OnboardingNavigationRoute {
    const val ONBOARDING_SCREEN_ROUTE = "onboarding_screen"
}
