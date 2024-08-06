package pl.dawidfendler.authentication.login.model

internal data class GoogleLoginModel(
    val isInitial: Boolean = true,
    val idToken: String = ""
)
