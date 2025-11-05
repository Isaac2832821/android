package com.example.snake1.data.repository

import com.example.snake1.data.models.Score
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SimpleScoreRepository {
    private val _scores = MutableStateFlow(
        listOf(
            Score(
                id = "score1",
                userId = "player1",
                username = "jugador1",
                gameId = "snake",
                gameName = "Snake Classic",
                score = 150
            ),
            Score(
                id = "score2",
                userId = "player1",
                username = "jugador1",
                gameId = "snake",
                gameName = "Snake Classic",
                score = 89
            )
        )
    )
    val scores: StateFlow<List<Score>> = _scores.asStateFlow()

    fun addScore(score: Score) {
        val newScore = score.copy(id = "score_${System.currentTimeMillis()}")
        _scores.value = (_scores.value + newScore).sortedByDescending { it.score }
    }

    fun getScoresByUser(userId: String): List<Score> {
        return _scores.value.filter { it.userId == userId }
    }

    fun getScoresByGame(gameId: String): List<Score> {
        return _scores.value.filter { it.gameId == gameId }.sortedByDescending { it.score }
    }

    fun getTopScores(limit: Int = 10): List<Score> {
        return _scores.value.sortedByDescending { it.score }.take(limit)
    }

    fun deleteScore(scoreId: String) {
        _scores.value = _scores.value.filter { it.id != scoreId }
    }
}