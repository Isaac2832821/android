package com.example.snake1.data.repository

import com.example.snake1.data.models.Game
import com.example.snake1.data.models.GameDifficulty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SimpleGameRepository {
    private val _games = MutableStateFlow(
        listOf(
            Game(
                id = "snake",
                name = "Snake Classic",
                description = "El clásico juego de la serpiente. Come manzanas y crece sin chocar contigo mismo.",
                difficulty = GameDifficulty.MEDIUM
            ),
            Game(
                id = "memory",
                name = "Memoria",
                description = "Juego de memoria con cartas. Encuentra las parejas.",
                difficulty = GameDifficulty.EASY,
                isActive = false
            ),
            Game(
                id = "puzzle",
                name = "Puzzle Numérico",
                description = "Ordena los números del 1 al 15 en el menor tiempo posible.",
                difficulty = GameDifficulty.HARD,
                isActive = false
            )
        )
    )
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    fun addGame(game: Game) {
        _games.value = _games.value + game
    }

    fun updateGame(game: Game) {
        _games.value = _games.value.map { 
            if (it.id == game.id) game else it 
        }
    }

    fun deleteGame(gameId: String) {
        _games.value = _games.value.filter { it.id != gameId }
    }

    fun getGameById(gameId: String): Game? {
        return _games.value.find { it.id == gameId }
    }

    fun getActiveGames(): List<Game> {
        return _games.value.filter { it.isActive }
    }
}