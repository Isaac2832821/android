package com.example.snake1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.snake1.ui.Screens.*
import com.example.snake1.data.repository.SimpleUserRepository
import com.example.snake1.data.repository.SimpleGameRepository
import com.example.snake1.data.repository.SimpleScoreRepository

@Composable
fun NavGraph(
    navController: NavHostController,
    userRepository: SimpleUserRepository,
    gameRepository: SimpleGameRepository,
    scoreRepository: SimpleScoreRepository
) {
    NavHost(navController = navController, startDestination = "login") {
        // Autenticación
        composable("login") { 
            SimpleLoginScreen(navController, userRepository) 
        }
        composable("register") { 
            RegisterScreen(navController, userRepository) 
        }
        
        // Pantallas principales
        composable("home") { 
            StartScreen(navController, userRepository, gameRepository) 
        }
        composable("game") { 
            RealSnakeGameScreen(navController, userRepository, scoreRepository) 
        }
        composable("rankings") { 
            RankingsScreen(navController, scoreRepository) 
        }
        composable("profile") { 
            ProfileScreen(navController, userRepository, scoreRepository) 
        }
        
        // Administración
        composable("admin") { 
            AdminScreen(navController, userRepository, gameRepository, scoreRepository) 
        }
        composable("admin/users") { 
            AdminUsersScreen(navController, userRepository) 
        }
        composable("admin/games") { 
            AdminGamesScreen(navController, gameRepository) 
        }
        composable("admin/scores") { 
            AdminScoresScreen(navController, scoreRepository) 
        }
        
        // Mantener compatibilidad
        composable("score") { 
            RankingsScreen(navController, scoreRepository) 
        }
    }
}
