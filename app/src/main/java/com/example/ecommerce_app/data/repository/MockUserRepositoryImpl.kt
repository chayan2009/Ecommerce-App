package com.example.ecommerce_app.data.repository
import com.example.ecommerce_app.domain.model.User
import com.example.ecommerce_app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockUserRepositoryImpl : UserRepository {

    private val users = mutableListOf(
        User(id = "1", name = "chayan", email = "chayan@gmail.com", password = "chayan"),
    )

    override fun getUsers(): Flow<List<User>> = flow { emit(users) }

    override suspend fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    override suspend fun registerUser(user: User): Boolean {
        if (users.any { it.email == user.email }) return false
        users.add(user)
        return true
    }

    override suspend fun login(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }
}
