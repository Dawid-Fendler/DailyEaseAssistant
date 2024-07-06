package pl.dawidfendler.onboarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.dawidfendler.onboarding.OnboardingScreen
import pl.dawidfendler.onboarding.OnboardingViewModel
import pl.dawidfendler.util.navigation.Navigation

fun NavGraphBuilder.onboardingRoute(
    navigateToAuth: () -> Unit
) {
    composable<Navigation.OnboardingNavigation> {
        val viewModel: OnboardingViewModel = hiltViewModel()

        OnboardingScreen(
            onFinishButtonClick = {
                viewModel.saveOnboardingDisplayed()
            },
            navigateToAuth = navigateToAuth
        )
    }
}