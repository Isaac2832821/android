package com.example.snake1.data.models

data class Score(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val gameId: String = "",
    val gameName: String = "",
    val score: Int = 0,
    val playedAt: Long = System.currentTimeMillis()
)