package pl.dawidfendler.dailyeaseassistant.ui

import pl.dawidfendler.util.navigation.Navigation

data class MainState(
    val navigation: Navigation = Navigation.OnboardingNavigation,
    val isStarting: Boolean = false
)
