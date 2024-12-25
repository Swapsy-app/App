package com.example.freeupcopy.utils


private const val ERR_LEN = "Minimum 8 character required."
private const val OUR_CURRENCY = "Coin"

data class ValidationResult(
    val isValid: Boolean = false,
    val errorMessage: String? = null
)

object Validator {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    private const val GSTIN_REGEX = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$"

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

    fun validateMrp(amount: String?): ValidationResult {
        return when {
            amount.isNullOrEmpty() -> ValidationResult(false, "Mrp cannot be empty")
            amount.toLongOrNull() == null || amount.toLong() <= 0 -> ValidationResult(false, "Mrp must be greater than zero")
            else -> ValidationResult(true)
        }
    }

    fun validateCashAmount(amount: String?, minEarnings: Long = 10): ValidationResult {
        return when {
            amount.isNullOrEmpty() -> ValidationResult(false, "Cash cannot be empty")
            amount.toLongOrNull() == null || amount.toLong() <= 0 -> ValidationResult(false, "Cash must be greater than zero")
            calculateTotalEarnings(amount.toLong(), "cat0") < minEarnings -> ValidationResult(false, "Cash is too low, you can sell using coins")
            else -> ValidationResult(true)
        }
    }

    fun validateCoinAmount(amount: String?, minCoins: Long = 10): ValidationResult {
        return when {
            amount.isNullOrEmpty() -> ValidationResult(false, "$OUR_CURRENCY cannot be empty")
            amount.toLongOrNull() == null || amount.toLong() < minCoins -> ValidationResult(
                false,
                "$OUR_CURRENCY must be greater than $minCoins"
            )

            else -> ValidationResult(true)

        }
    }

    fun validateGstin(gstin: String): ValidationResult {
        return when {
            gstin.isEmpty() -> ValidationResult(true)
            !gstin.matches(GSTIN_REGEX.toRegex()) -> ValidationResult(false, "Invalid GSTIN format.")
            else -> ValidationResult(true)
        }
    }
}