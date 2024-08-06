package pl.dawidfendler.domain.validator

data class EmailValidResult(
    val isEmailError: Boolean = false,
    val errorMessage: String = ""
)

data class PasswordValidResult(
    val isPasswordError: Boolean = false,
    val errorMessage: String = ""
)
