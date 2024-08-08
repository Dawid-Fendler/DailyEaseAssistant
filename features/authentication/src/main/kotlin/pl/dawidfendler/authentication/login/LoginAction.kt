package pl.dawidfendler.authentication.login

sealed interface LoginAction {
    data object OnTogglePasswordVisibilityClick : LoginAction
    data object OnLoginClick : LoginAction
    data class OnLoginUpdate(val login: String) : LoginAction
    data class OnPasswordUpdate(val password: String) : LoginAction
}
