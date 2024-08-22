package pl.dawidfendler.authentication.login

internal sealed interface LoginAction {
    data object OnTogglePasswordVisibilityClick : LoginAction
    data object OnLoginClick : LoginAction
    data object OnGoogleLoginError : LoginAction
    data class OnGoogleLoginClick(val idToken: String) : LoginAction
    data class OnLoginUpdate(val login: String) : LoginAction
    data class OnPasswordUpdate(val password: String) : LoginAction
}
