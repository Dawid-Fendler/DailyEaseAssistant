package pl.dawidfendler.domain.validator

import android.util.Patterns
import javax.inject.Inject

class EmailValidator @Inject constructor() {

    fun validateEmail(email: String): EmailValidResult {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailValidResult()
        } else {
            EmailValidResult(
                isEmailError = true,
                errorMessage = EMAIL_HAS_WRONG_FORMAT
            )
        }
    }
}

private const val EMAIL_HAS_WRONG_FORMAT =
    "The email has an incorrect format, it must look like this: abc@test.pl"
