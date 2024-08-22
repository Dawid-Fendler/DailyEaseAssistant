package pl.dawidfendler.authentication.login

import androidx.annotation.StringRes

internal data class LoginState(
    val email: String = "",
    val password: String = "",
    val isError: Boolean = false,
    @StringRes val errorMessage: Int = 0,
    val isLogin: Boolean = false,
    val isPasswordVisible: Boolean = false
)
