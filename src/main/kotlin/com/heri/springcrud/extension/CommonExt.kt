package com.heri.springcrud.extension

import java.util.regex.Pattern

val EMAIL_ADDRESS = Pattern.compile(
         "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
         "\\@" +
         "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
         "(" +
         "\\." +
         "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
         ")+"
)

val PERSON_NAME = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")

fun String.isEmail(): Boolean {
    return EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isNonNumeric(): Boolean {
    val p = "\\D+".toRegex()
    return matches(p)
}

fun String.isNumeric(): Boolean {
    val p = ".*\\d+.*".toRegex()
    return matches(p)
}

fun String.isNameFormat(): Boolean {
    return PERSON_NAME.matcher(this).matches()
}