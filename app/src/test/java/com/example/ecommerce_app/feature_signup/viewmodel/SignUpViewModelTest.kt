package com.example.ecommerce_app.feature_signup.viewmodel

import app.cash.turbine.test
import com.example.ecommerce_app.core.common.ValidationUtil
import com.example.ecommerce_app.core.datastore.UserPreferences
import com.example.ecommerce_app.feature_splash.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SignUpViewModel
    private val userPreferences: UserPreferences = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = SignUpViewModel(userPreferences)
    }

    @Test
    fun onNameChange_updatesNameAndValidationError() = runTest {
        val validName = "chayan"
        val invalidName = ""

        viewModel.onNameChange(validName)

        viewModel.name.test {
            assertEquals(validName, awaitItem())
        }
        viewModel.nameError.test {
            assertNull(awaitItem())
        }

        viewModel.onNameChange(invalidName)

        viewModel.nameError.test {
            assertEquals(ValidationUtil.validateUsername(invalidName), awaitItem())
        }
    }

    @Test
    fun onEmailChange_updatesEmailAndValidationError() = runTest {
        val validEmail = "test@example.com"
        val invalidEmail = "invalid"

        viewModel.onEmailChange(validEmail)

        viewModel.email.test {
            assertEquals(validEmail, awaitItem())
        }
        viewModel.emailError.test {
            assertNull(awaitItem())
        }

        viewModel.onEmailChange(invalidEmail)

        viewModel.emailError.test {
            assertEquals(ValidationUtil.validateUsername(invalidEmail), awaitItem())
        }
    }

    @Test
    fun onPasswordChange_updatesPasswordAndValidationError() = runTest {
        val validPassword = "Secure123!"
        val invalidPassword = "weak"

        viewModel.onPasswordChange(validPassword)

        viewModel.password.test {
            assertEquals(validPassword, awaitItem())
        }
        viewModel.passwordError.test {
            assertNull(awaitItem())
        }

        viewModel.onPasswordChange(invalidPassword)

        viewModel.passwordError.test {
            assertEquals(ValidationUtil.validatePassword(invalidPassword), awaitItem())
        }
    }

    @Test
    fun validateForm_withValidInputs_returnsTrue() = runTest {
        viewModel.onNameChange("John Doe")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("Secure123!")

        val isValid = viewModel.validateForm()

        assertTrue(isValid)
    }

    @Test
    fun validateForm_withInvalidInputs_returnsFalse() = runTest {
        viewModel.onNameChange("")
        viewModel.onEmailChange("invalid")
        viewModel.onPasswordChange("weak")

        val isValid = viewModel.validateForm()

        assertFalse(isValid)
    }

    @Test
    fun saveUserIDPassword_savesCredentials() = runTest {
        val username = "testuser"
        val password = "testpass123"
        viewModel.onNameChange(username)
        viewModel.onPasswordChange(password)
        coEvery { userPreferences.saveUserCredentials(any(), any()) } returns Unit

        viewModel.saveUserIDPassword()

        coVerify(exactly = 1) {
            userPreferences.saveUserCredentials(username, password)
        }
    }
}