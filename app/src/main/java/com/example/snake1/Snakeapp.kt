package com.example.snake1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.snake1.navigation.NavGraph
import com.example.snake1.ui.theme.Snake1Theme
import com.example.snake1.data.repository.SimpleUserRepository
import com.example.snake1.data.repository.SimpleGameRepository
import com.example.snake1.data.repository.SimpleScoreRepository

@Composable
fun SnakeApp() {
    val navController = rememberNavController()
    
    // Repositorios simples sin almacenamiento complejo
    val userRepository = remember { SimpleUserRepository() }
    val gameRepository = remember { SimpleGameRepository() }
    val scoreRepository = remember { SimpleScoreRepository() }
    
    Snake1Theme {
        NavGraph(
            navController = navController,
            userRepository = userRepository,
            gameRepository = gameRepository,
            scoreRepository = scoreRepository
        )
    }
}