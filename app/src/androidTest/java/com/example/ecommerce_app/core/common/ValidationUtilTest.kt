package com.example.ecommerce_app.core.common

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationUtilTest {

    @Test
    fun validateName_returnsErrorForEmptyName() {
        val result = ValidationUtil.validateName("")
        assertEquals("Name cannot be empty", result)
    }

    @Test
    fun validateName_returnsErrorForShortName() {
        val result = ValidationUtil.validateName("Jo")
        assertEquals("Name must be at least 3 characters", result)
    }

    @Test
    fun validateName_returnsNullForValidName() {
        val result = ValidationUtil.validateName("John Doe")
        assertEquals(null, result)
    }

    @Test
    fun validateEmail_returnsErrorForInvalidEmail() {
        val result = ValidationUtil.validateEmail("invalid-email")
        assertEquals("Enter a valid email address", result)
    }

    @Test
    fun validateEmail_returnsNullForValidEmail() {
        val result = ValidationUtil.validateEmail("user@example.com")
        assertEquals(null, result)
    }

    @Test
    fun validatePassword_returnsErrorForWeakPassword() {
        val result = ValidationUtil.validatePassword("abc")
        assertEquals("Password must be at least 3 characters", result)
    }

    @Test
    fun validatePassword_returnsNullForStrongPassword() {
        val result = ValidationUtil.validatePassword("StrongP@ss123")
        assertEquals(null, result)
    }
}

