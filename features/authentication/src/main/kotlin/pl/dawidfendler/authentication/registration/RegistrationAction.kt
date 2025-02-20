package pl.dawidfendler.authentication.registration

sealed interface RegistrationAction {
    data object OnTogglePasswordVisibilityClick : RegistrationAction
    data object OnRegisterClick : RegistrationAction
    data object OnGoogleLoginError : RegistrationAction
    data class OnGoogleLoginClick(val idToken: String) : RegistrationAction
    data class OnLoginUpdate(val login: String) : RegistrationAction
    data class OnPasswordUpdate(val password: String) : RegistrationAction
}
