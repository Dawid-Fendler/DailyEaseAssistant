package pl.dawidfendler.authentication.registration

import pl.dawidfendler.util.UiText

sealed interface RegistrationEvent {
    data object RegistrationSuccess : RegistrationEvent
    data class Error(val error: UiText) : RegistrationEvent
}
