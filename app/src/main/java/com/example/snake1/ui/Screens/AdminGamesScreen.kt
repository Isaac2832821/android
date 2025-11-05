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
import com.example.snake1.data.repository.SimpleGameRepository
import com.example.snake1.data.models.Game
import com.example.snake1.data.models.GameDifficulty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGamesScreen(
    navController: NavController,
    gameRepository: SimpleGameRepository
) {
    val games by gameRepository.games.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<Game?>(null) }
    var showDeleteDialog by remember { mutableStateOf<Game?>(null) }

    // Dialog para agregar juego
    if (showAddDialog) {
        var gameName by remember { mutableStateOf("") }
        var gameDescription by remember { mutableStateOf("") }
        var gameDifficulty by remember { mutableStateOf(GameDifficulty.EASY) }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Agregar Nuevo Juego") },
            text = {
                Column {
                    OutlinedTextField(
                        value = gameName,
                        onValueChange = { gameName = it },
                        label = { Text("Nombre del juego") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = gameDescription,
                        onValueChange = { gameDescription = it },
                        label = { Text("Descripción") },
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Dificultad:", style = MaterialTheme.typography.bodyMedium)
                    Column {
                        GameDifficulty.values().forEach { difficulty ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = gameDifficulty == difficulty,
                                    onClick = { gameDifficulty = difficulty }
                                )
                                Text(
                                    text = difficulty.name,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (gameName.isNotBlank() && gameDescription.isNotBlank()) {
                            val newGame = Game(
                                id = "game_${System.currentTimeMillis()}",
                                name = gameName,
                                description = gameDescription,
                                difficulty = gameDifficulty,
                                isActive = false // Los nuevos juegos empiezan inactivos
                            )
                            gameRepository.addGame(newGame)
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog para editar juego
    showEditDialog?.let { game ->
        var editedName by remember { mutableStateOf(game.name) }
        var editedDescription by remember { mutableStateOf(game.description) }
        var editedDifficulty by remember { mutableStateOf(game.difficulty) }
        var editedIsActive by remember { mutableStateOf(game.isActive) }

        AlertDialog(
            onDismissRequest = { showEditDialog = null },
            title = { Text("Editar Juego") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nombre del juego") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedDescription,
                        onValueChange = { editedDescription = it },
                        label = { Text("Descripción") },
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Dificultad:", style = MaterialTheme.typography.bodyMedium)
                    Column {
                        GameDifficulty.values().forEach { difficulty ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = editedDifficulty == difficulty,
                                    onClick = { editedDifficulty = difficulty }
                                )
                                Text(
                                    text = difficulty.name,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = editedIsActive,
                            onCheckedChange = { editedIsActive = it }
                        )
                        Text(
                            text = "Juego activo",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val updatedGame = game.copy(
                            name = editedName,
                            description = editedDescription,
                            difficulty = editedDifficulty,
                            isActive = editedIsActive
                        )
                        gameRepository.updateGame(updatedGame)
                        showEditDialog = null
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog para eliminar juego
    showDeleteDialog?.let { game ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Eliminar Juego") },
            text = { Text("¿Estás seguro de que quieres eliminar '${game.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        gameRepository.deleteGame(game.id)
                        showDeleteDialog = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎮 Gestión de Juegos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("←", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Text("➕")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
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
                            text = "📊 Resumen",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Total de juegos: ${games.size}")
                        Text("Juegos activos: ${games.count { it.isActive }}")
                        Text("Juegos inactivos: ${games.count { !it.isActive }}")
                    }
                }
            }

            items(games) { game ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = game.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = game.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    Badge(
                                        containerColor = when (game.difficulty) {
                                            GameDifficulty.EASY -> MaterialTheme.colorScheme.secondary
                                            GameDifficulty.MEDIUM -> MaterialTheme.colorScheme.tertiary
                                            GameDifficulty.HARD -> MaterialTheme.colorScheme.error
                                        }
                                    ) {
                                        Text(game.difficulty.name)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Badge(
                                        containerColor = if (game.isActive) 
                                            MaterialTheme.colorScheme.primary 
                                        else 
                                            MaterialTheme.colorScheme.outline
                                    ) {
                                        Text(if (game.isActive) "ACTIVO" else "INACTIVO")
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showEditDialog = game },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("✏️ Editar")
                            }
                            
                            OutlinedButton(
                                onClick = { showDeleteDialog = game },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("🗑️ Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}