package pl.dawidfendler.authentication.registration

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val isRegistering: Boolean = false
)
