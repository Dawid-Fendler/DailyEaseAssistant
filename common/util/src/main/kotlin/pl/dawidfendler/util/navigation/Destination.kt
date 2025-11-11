package pl.dawidfendler.util.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {
    @Serializable
    data object Onboarding : Destination()

    @Serializable
    data object Login : Destination()

    @Serializable
    data object Registration : Destination()

    @Serializable
    data object AuthGraph : Destination()

    @Serializable
    data object MainGraph : Destination()

    @Serializable
    data object Home : Destination()
}
