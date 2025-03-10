package com.example.ecommerce_app.domain.repository


import com.example.ecommerce_app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun getUserByEmail(email: String): User?
    suspend fun registerUser(user: User): Boolean
    suspend fun login(user: String, password: String): Boolean
}
