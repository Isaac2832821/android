package com.example.snake1.data.repository

import com.example.snake1.data.models.User
import com.example.snake1.data.models.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SimpleUserRepository {
    private val _users = MutableStateFlow(
        listOf(
            User(
                id = "admin1",
                username = "admin",
                email = "admin@snake.com",
                role = UserRole.ADMIN
            ),
            User(
                id = "player1",
                username = "jugador1",
                email = "player@snake.com",
                role = UserRole.PLAYER,
                totalScore = 150,
                gamesPlayed = 5
            )
        )
    )
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(username: String, password: String): Boolean {
        val user = _users.value.find { it.username == username }
        return if (user != null) {
            _currentUser.value = user
            true
        } else {
            false
        }
    }

    fun register(username: String, email: String, password: String): Boolean {
        val existingUser = _users.value.find { it.username == username || it.email == email }
        return if (existingUser == null) {
            val newUser = User(
                id = "user_${System.currentTimeMillis()}",
                username = username,
                email = email,
                role = UserRole.PLAYER
            )
            _users.value = _users.value + newUser
            _currentUser.value = newUser
            true
        } else {
            false
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun updateUserScore(userId: String, newScore: Int) {
        _users.value = _users.value.map { user ->
            if (user.id == userId) {
                user.copy(
                    totalScore = user.totalScore + newScore,
                    gamesPlayed = user.gamesPlayed + 1
                )
            } else {
                user
            }
        }
        
        _currentUser.value?.let { current ->
            if (current.id == userId) {
                _currentUser.value = _users.value.find { it.id == userId }
            }
        }
    }

    fun deleteUser(userId: String) {
        _users.value = _users.value.filter { it.id != userId }
    }

    fun updateUser(user: User) {
        _users.value = _users.value.map { 
            if (it.id == user.id) user else it 
        }
        
        if (_currentUser.value?.id == user.id) {
            _currentUser.value = user
        }
    }
}