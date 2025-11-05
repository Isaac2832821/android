package com.example.snake1.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.snake1.data.repository.SimpleUserRepository
import com.example.snake1.data.repository.SimpleGameRepository
import com.example.snake1.data.repository.SimpleScoreRepository
import com.example.snake1.data.models.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    userRepository: SimpleUserRepository,
    gameRepository: SimpleGameRepository,
    scoreRepository: SimpleScoreRepository
) {
    val currentUser by userRepository.currentUser.collectAsState()
    val users by userRepository.users.collectAsState()
    val games by gameRepository.games.collectAsState()
    val scores by scoreRepository.scores.collectAsState()

    // Verificar que el usuario sea admin
    if (currentUser?.role != UserRole.ADMIN) {
        LaunchedEffect(Unit) {
            navController.navigateUp()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚙️ Panel de Administración") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("←", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Estadísticas generales
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
                            text = "📊 Estadísticas del Sistema",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AdminStatCard(
                                title = "Usuarios",
                                value = "${users.size}",
                                icon = "👥"
                            )
                            
                            AdminStatCard(
                                title = "Juegos",
                                value = "${games.size}",
                                icon = "🎮"
                            )
                            
                            AdminStatCard(
                                title = "Partidas",
                                value = "${scores.size}",
                                icon = "📈"
                            )
                        }
                    }
                }
            }

            item {
                // Acciones de administración
                Text(
                    text = "🛠️ Gestión",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("admin/users") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("👥 Usuarios")
                    }
                    
                    Button(
                        onClick = { navController.navigate("admin/games") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("🎮 Juegos")
                    }
                }
            }

            item {
                Button(
                    onClick = { navController.navigate("admin/scores") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📊 Gestionar Puntuaciones")
                }
            }

            item {
                Text(
                    text = "👥 Usuarios Recientes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(users.takeLast(5).reversed()) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = user.username,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Badge(
                            containerColor = if (user.role == UserRole.ADMIN) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.secondary
                        ) {
                            Text(
                                text = if (user.role == UserRole.ADMIN) "ADMIN" else "PLAYER"
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "🎮 Estado de Juegos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(games) { game ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = game.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = game.difficulty.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Badge(
                            containerColor = if (game.isActive) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.error
                        ) {
                            Text(
                                text = if (game.isActive) "ACTIVO" else "INACTIVO"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    icon: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}