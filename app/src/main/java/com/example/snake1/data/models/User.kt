package com.example.snake1.data.models

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val role: UserRole = UserRole.PLAYER,
    val totalScore: Int = 0,
    val gamesPlayed: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserRole {
    PLAYER,
    ADMIN
}