package com.example.snake1.data.models

data class Game(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val difficulty: GameDifficulty = GameDifficulty.EASY,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class GameDifficulty {
    EASY,
    MEDIUM,
    HARD
}