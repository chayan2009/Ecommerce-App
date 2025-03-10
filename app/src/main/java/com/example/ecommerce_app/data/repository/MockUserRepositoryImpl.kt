package com.example.ecommerce_app.data.repository
import com.example.ecommerce_app.domain.model.User
import com.example.ecommerce_app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockUserRepositoryImpl  @Inject constructor(): UserRepository {
    private val users = mutableListOf(
        User(name = "chayan",password = "chayan"),
    )

    override fun getUsers(): Flow<List<User>> = flow { emit(users) }

    override suspend fun getUserByEmail(email: String): User? {
        return users.find { it.name == email }
    }

    override suspend fun registerUser(user: User): Boolean {
        if (users.any { it.name == user.name }) return false
        users.add(user)
        return true
    }

    override suspend fun login(email: String, password: String): Boolean {
        return users.any { it.name == email && it.password == password }
    }
}
