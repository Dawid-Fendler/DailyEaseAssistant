package pl.dawidfendler.util.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Onboarding : Destination()

    @Serializable
    data object Login : Destination()

    @Serializable
    data object Registration : Destination()

    @Serializable
    data object Home : Destination()

    @Serializable
    data object FinanceManager : Destination()
}
