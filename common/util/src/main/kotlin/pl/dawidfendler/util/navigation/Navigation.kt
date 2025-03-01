package pl.dawidfendler.util.navigation

import kotlinx.serialization.Serializable

sealed class Navigation {
    @Serializable
    data object OnboardingNavigation : Navigation()

    @Serializable
    data object LoginNavigation : Navigation()

    @Serializable
    data object RegistrationNavigation : Navigation()

    @Serializable
    data object HomeNavigation : Navigation()

    @Serializable
    data object FinanceManager : Navigation()
}
