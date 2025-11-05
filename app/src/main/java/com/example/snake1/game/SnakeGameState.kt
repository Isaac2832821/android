package com.example.snake1.game

import androidx.compose.runtime.Stable
import kotlin.random.Random

@Stable
data class Position(val x: Int, val y: Int)

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

enum class GameState {
    IDLE, PLAYING, PAUSED, GAME_OVER
}

data class SnakeGameState(
    val snake: List<Position> = listOf(Position(10, 10)),
    val food: Position = Position(5, 5),
    val direction: Direction = Direction.RIGHT,
    val gameState: GameState = GameState.IDLE,
    val score: Int = 0,
    val boardWidth: Int = 20,
    val boardHeight: Int = 20
) {
    fun moveSnake(): SnakeGameState {
        if (gameState != GameState.PLAYING) return this
        
        val head = snake.first()
        val newHead = when (direction) {
            Direction.UP -> Position(head.x, head.y - 1)
            Direction.DOWN -> Position(head.x, head.y + 1)
            Direction.LEFT -> Position(head.x - 1, head.y)
            Direction.RIGHT -> Position(head.x + 1, head.y)
        }
        
        // Verificar colisiones con paredes
        if (newHead.x < 0 || newHead.x >= boardWidth || 
            newHead.y < 0 || newHead.y >= boardHeight) {
            return copy(gameState = GameState.GAME_OVER)
        }
        
        // Verificar colisiones con el cuerpo
        if (snake.contains(newHead)) {
            return copy(gameState = GameState.GAME_OVER)
        }
        
        val newSnake = listOf(newHead) + snake
        
        // Verificar si comió la comida
        return if (newHead == food) {
            copy(
                snake = newSnake,
                food = generateNewFood(newSnake),
                score = score + 10
            )
        } else {
            copy(
                snake = newSnake.dropLast(1)
            )
        }
    }
    
    fun changeDirection(newDirection: Direction): SnakeGameState {
        // No permitir movimiento en dirección opuesta
        val oppositeDirection = when (direction) {
            Direction.UP -> Direction.DOWN
            Direction.DOWN -> Direction.UP
            Direction.LEFT -> Direction.RIGHT
            Direction.RIGHT -> Direction.LEFT
        }
        
        return if (newDirection != oppositeDirection) {
            copy(direction = newDirection)
        } else {
            this
        }
    }
    
    private fun generateNewFood(snake: List<Position>): Position {
        var newFood: Position
        do {
            newFood = Position(
                Random.nextInt(boardWidth),
                Random.nextInt(boardHeight)
            )
        } while (snake.contains(newFood))
        return newFood
    }
    
    fun startGame(): SnakeGameState {
        return copy(
            snake = listOf(Position(10, 10)),
            food = Position(5, 5),
            direction = Direction.RIGHT,
            gameState = GameState.PLAYING,
            score = 0
        )
    }
    
    fun pauseGame(): SnakeGameState {
        return copy(gameState = if (gameState == GameState.PLAYING) GameState.PAUSED else GameState.PLAYING)
    }
    
    fun resetGame(): SnakeGameState {
        return SnakeGameState()
    }
}