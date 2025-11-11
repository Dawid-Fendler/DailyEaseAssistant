package pl.dawidfendler.dailyeaseassistant.ui

import pl.dawidfendler.util.navigation.Destination

data class MainState(
    val navigation: Destination = Destination.Onboarding,
    val isStarting: Boolean = false,
    val isOnboardingToDisplay: Boolean = false
)
