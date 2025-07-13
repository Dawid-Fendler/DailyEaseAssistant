package pl.dawidfendler.util.ext

import pl.dawidfendler.util.Constants.ZERO

fun emptyString() = ""

fun normalizeValue(value: String, input: String): String {
    return if (value == ZERO && input.length == 2 && input[0] == '0' && input[1].isDigit() && input[1] != '0') {
        input[1].toString()
    } else if (value == ZERO && input != ZERO) {
        input.removePrefix(ZERO)
    } else {
        input
    }
}