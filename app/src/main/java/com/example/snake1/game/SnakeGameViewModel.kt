package com.example.snake1.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SnakeGameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(SnakeGameState())
    val gameState: StateFlow<SnakeGameState> = _gameState.asStateFlow()
    
    private var gameSpeed = 300L // milliseconds
    
    fun startGame() {
        _gameState.value = _gameState.value.startGame()
        startGameLoop()
    }
    
    fun pauseGame() {
        _gameState.value = _gameState.value.pauseGame()
    }
    
    fun resetGame() {
        _gameState.value = _gameState.value.resetGame()
    }
    
    fun changeDirection(direction: Direction) {
        _gameState.value = _gameState.value.changeDirection(direction)
    }
    
    private fun startGameLoop() {
        viewModelScope.launch {
            while (_gameState.value.gameState == GameState.PLAYING) {
                delay(gameSpeed)
                if (_gameState.value.gameState == GameState.PLAYING) {
                    _gameState.value = _gameState.value.moveSnake()
                }
            }
        }
    }
    
    fun increaseSpeed() {
        if (gameSpeed > 100L) {
            gameSpeed -= 20L
        }
    }
    
    fun getCurrentScore(): Int = _gameState.value.score
}