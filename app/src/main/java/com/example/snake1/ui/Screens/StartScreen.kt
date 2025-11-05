package com.example.snake1.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.snake1.data.repository.SimpleUserRepository
import com.example.snake1.data.repository.SimpleGameRepository
import com.example.snake1.data.models.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    navController: NavController,
    userRepository: SimpleUserRepository,
    gameRepository: SimpleGameRepository
) {
    val currentUser by userRepository.currentUser.collectAsState()
    val games by gameRepository.games.collectAsState()
    val activeGames = games.filter { it.isActive }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centro de Entretenimiento") },
                actions = {
                    TextButton(
                        onClick = {
                            userRepository.logout()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    ) {
                        Text("Salir")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Saludo al usuario
            currentUser?.let { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "¡Hola, ${user.username}! 👋",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Puntuación total: ${user.totalScore} | Partidas: ${user.gamesPlayed}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (user.role == UserRole.ADMIN) {
                            Text(
                                text = "👑 Administrador",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Navegación rápida
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { navController.navigate("rankings") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🏆 Rankings")
                }
                
                Button(
                    onClick = { navController.navigate("profile") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("👤 Perfil")
                }
            }

            // Panel de administrador
            currentUser?.let { user ->
                if (user.role == UserRole.ADMIN) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("admin") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("⚙️ Panel de Administración")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Juegos Disponibles",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(activeGames) { game ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            when (game.id) {
                                "snake" -> navController.navigate("game")
                                else -> {
                                    // Otros juegos no implementados aún
                                }
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = game.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                
                                Badge {
                                    Text(game.difficulty.name)
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = game.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            if (game.id != "snake") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Próximamente...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
