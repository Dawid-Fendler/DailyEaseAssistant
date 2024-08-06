package pl.dawidfendler.domain.validator

import pl.dawidfendler.domain.util.Constants.PASSWORD_SPECIAL_CHARACTER_REGEX
import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    fun validatePassword(password: String): PasswordValidResult {
        val errorsList = buildList {
            if (password.length < 8) {
                add(PASSWORD_IS_TOO_SHORT)
            }
            if (!password.any { it.isUpperCase() }) {
                add(PASSWORD_NOT_CONTAIN_UPPER_LETTER)
            }
            if (!password.any { it.isDigit() }) {
                add(PASSWORD_NOT_CONTAIN_DIGITS)
            }
            if (!password.contains(PASSWORD_SPECIAL_CHARACTER_REGEX.toRegex())) {
                add(PASSWORD_NOT_CONTAIN_SPECIAL_CHARACTER)
            }
        }
        return PasswordValidResult(
            isPasswordError = errorsList.isNotEmpty(),
            errorMessage = if (errorsList.isEmpty()) "" else preparePasswordErrorMessage(errorsList)
        )
    }
}

private fun preparePasswordErrorMessage(errors: List<String>): String {
    var errorMessage = ""
    errors.forEachIndexed { index, error ->
        errorMessage += if (index == errors.size) {
            "- $error"
        } else {
            "- $error \n"
        }
    }
    return errorMessage
}

private const val PASSWORD_IS_TOO_SHORT =
    "The password must be at least 8 characters sign"
private const val PASSWORD_NOT_CONTAIN_UPPER_LETTER = "The password not contain upper letter"
private const val PASSWORD_NOT_CONTAIN_DIGITS = "The password not contain digit"
private const val PASSWORD_NOT_CONTAIN_SPECIAL_CHARACTER =
    "The password not contain special character"
