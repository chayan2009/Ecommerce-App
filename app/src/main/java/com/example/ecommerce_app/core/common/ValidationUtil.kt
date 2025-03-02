package com.example.ecommerce_app.core.common

object ValidationUtil {
    fun validateName(name: String): String? {
        return if (name.isBlank()) "Name cannot be empty" else null
    }

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email cannot be empty"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password cannot be empty"
            password.length < 6 -> "Password must be at least 6 characters"
            !password.any { it.isDigit() } -> "Password must contain at least one number"
            else -> null
        }
    }
}
