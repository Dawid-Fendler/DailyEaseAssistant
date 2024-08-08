package pl.dawidfendler.authentication.login

import pl.dawidfendler.util.UiText

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class Error(val error: UiText) : LoginEvent
}
