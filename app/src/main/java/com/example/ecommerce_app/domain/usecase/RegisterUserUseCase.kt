package com.example.ecommerce_app.domain.usecase
import com.example.ecommerce_app.domain.model.User
import com.example.ecommerce_app.domain.repository.UserRepository

class RegisterUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): Boolean {
        return repository.registerUser(user)
    }
}
