package com.example.ecommerce_app.domain.usecase


import com.example.ecommerce_app.domain.repository.UserRepository

class LoginUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.login(email, password)
    }
}
