package com.example.freeupcopy.utils


data class ValidationResult(
    val isValid: Boolean = false,
    val errorMessage: String? = null
)

object Validator {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun validateName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult(false, "Name cannot be empty.")
            name.length < 2 -> ValidationResult(false, "Name must be at least 2 characters long.")
            else -> ValidationResult(true)
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isEmpty() -> ValidationResult(false, "Email cannot be empty.")
            !email.matches(EMAIL_REGEX.toRegex()) -> ValidationResult(false, "Invalid email format.")
            else -> ValidationResult(true)
        }
    }

    fun validateMobile(mobile: String): ValidationResult {
        return when {
            mobile.isEmpty() -> ValidationResult(false, "Mobile number cannot be empty.")
            !mobile.matches("\\d{10}".toRegex()) -> ValidationResult(false, "Mobile number must be 10 digits.")
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult(false, "Password cannot be empty.")
            password.length < 6 -> ValidationResult(false, ERR_LEN)
//            password.none { it.isDigit() } -> ValidationResult(false, "Password must contain at least one digit.")
//            password.none { it.isUpperCase() } -> ValidationResult(false, "Password must contain at least one uppercase letter.")
//            password.none { it.isLowerCase() } -> ValidationResult(false, "Password must contain at least one lowercase letter.")
//            password.none { !it.isLetterOrDigit() } -> ValidationResult(false, "Password must contain at least one special character.")
            else -> ValidationResult(true)
        }
    }
}




//data class ValidationResult(
//    val status: Boolean = false,
//    val message: String = ""
//)

//internal fun isValidPassword(password: String): Boolean {
//    if (password.length < 8) return false
//    if (password.filter { it.isDigit() }.firstOrNull() == null) return false
//    if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
//    if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
//    if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false
//
//    return true
//}

private const val ERR_LEN = "Minimum 8 character required."