package com.example.snake1.ui.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snake1.data.repository.SimpleUserRepository
import com.example.snake1.data.repository.SimpleScoreRepository
import com.example.snake1.data.models.Score
import com.example.snake1.game.*
import com.example.snake1.ui.components.TouchControlArea
import com.example.snake1.ui.components.AnimatedButton
import com.example.snake1.ui.components.PulsingIcon
import com.example.snake1.utils.HapticFeedback
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealSnakeGameScreen(
    navController: NavController,
    userRepository: SimpleUserRepository,
    scoreRepository: SimpleScoreRepository,
    viewModel: SnakeGameViewModel = viewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    val currentUser by userRepository.currentUser.collectAsState()
    var showGameOverDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val hapticFeedback = remember { HapticFeedback(context) }
    
    // Mostrar dialog cuando el juego termine y vibrar
    LaunchedEffect(gameState.gameState) {
        if (gameState.gameState == GameState.GAME_OVER) {
            hapticFeedback.vibrateGameOver()
            showGameOverDialog = true
        }
    }
    
    // Vibrar cuando aumenta la puntuación
    LaunchedEffect(gameState.score) {
        if (gameState.score > 0) {
            hapticFeedback.vibrateScoreIncrease()
        }
    }
    
    // Dialog de fin de juego
    if (showGameOverDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("¡Juego Terminado!") },
            text = { 
                Column {
                    Text("🐍 ¡Bien jugado!")
                    Text("Puntuación final: ${gameState.score}")
                    Text("Longitud de la serpiente: ${gameState.snake.size}")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Guardar puntuación
                        currentUser?.let { user ->
                            val score = Score(
                                userId = user.id,
                                username = user.username,
                                gameId = "snake",
                                gameName = "Snake Classic",
                                score = gameState.score
                            )
                            scoreRepository.addScore(score)
                            userRepository.updateUserScore(user.id, gameState.score)
                        }
                        showGameOverDialog = false
                        viewModel.resetGame()
                        navController.navigate("home")
                    }
                ) {
                    Text("Guardar y Salir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showGameOverDialog = false
                        viewModel.resetGame()
                    }
                ) {
                    Text("Jugar de Nuevo")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🐍 Snake Game") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("←", style = MaterialTheme.typography.headlineSmall)
                    }
                },
                actions = {
                    if (gameState.gameState == GameState.PLAYING) {
                        IconButton(onClick = { viewModel.pauseGame() }) {
                            Text("⏸️")
                        }
                    } else if (gameState.gameState == GameState.PAUSED) {
                        IconButton(onClick = { viewModel.pauseGame() }) {
                            Text("▶️")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Información del juego
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Puntuación: ${gameState.score}",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = "Longitud: ${gameState.snake.size}",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tablero de juego
            GameBoard(
                gameState = gameState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Estado del juego
            when (gameState.gameState) {
                GameState.IDLE -> {
                    AnimatedButton(
                        onClick = { 
                            hapticFeedback.vibrateGameStart()
                            viewModel.startGame() 
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PulsingIcon {
                            Text("🎮 Iniciar Juego")
                        }
                    }
                }
                GameState.PAUSED -> {
                    Text(
                        text = "⏸️ PAUSADO",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                GameState.PLAYING -> {
                    Text(
                        text = "🎮 JUGANDO",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                GameState.GAME_OVER -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💀 GAME OVER",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.resetGame() }
                        ) {
                            Text("🔄 Reiniciar")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Controles direccionales
            if (gameState.gameState == GameState.PLAYING || gameState.gameState == GameState.PAUSED) {
                Column {
                    // Controles táctiles
                    TouchControlArea(
                        onDirectionChange = { direction ->
                            viewModel.changeDirection(direction)
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Controles con botones
                    GameControls(
                        onDirectionChange = { direction ->
                            viewModel.changeDirection(direction)
                        }
                    )
                }
            }
            
            // Spacer para asegurar que hay espacio al final
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun GameBoard(
    gameState: SnakeGameState,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    
    Canvas(
        modifier = modifier.background(Color(0xFF2E7D32))
    ) {
        val cellSize = size.width / gameState.boardWidth
        
        // Dibujar la serpiente
        gameState.snake.forEachIndexed { index, position ->
            val color = if (index == 0) Color(0xFF4CAF50) else Color(0xFF66BB6A) // Cabeza más oscura
            drawRect(
                color = color,
                topLeft = Offset(
                    position.x * cellSize,
                    position.y * cellSize
                ),
                size = Size(cellSize * 0.9f, cellSize * 0.9f)
            )
        }
        
        // Dibujar la comida (manzana)
        drawCircle(
            color = Color(0xFFFF5722),
            radius = cellSize * 0.4f,
            center = Offset(
                gameState.food.x * cellSize + cellSize * 0.5f,
                gameState.food.y * cellSize + cellSize * 0.5f
            )
        )
    }
}

@Composable
fun GameControls(
    onDirectionChange: (Direction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón UP
        Button(
            onClick = { onDirectionChange(Direction.UP) },
            modifier = Modifier.size(50.dp),
            shape = CircleShape
        ) {
            Text("⬆️", style = MaterialTheme.typography.bodyLarge)
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Botones LEFT y RIGHT
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onDirectionChange(Direction.LEFT) },
                modifier = Modifier.size(50.dp),
                shape = CircleShape
            ) {
                Text("⬅️", style = MaterialTheme.typography.bodyLarge)
            }
            
            Button(
                onClick = { onDirectionChange(Direction.RIGHT) },
                modifier = Modifier.size(50.dp),
                shape = CircleShape
            ) {
                Text("➡️", style = MaterialTheme.typography.bodyLarge)
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Botón DOWN - ¡ESTE ES EL QUE FALTABA!
        Button(
            onClick = { onDirectionChange(Direction.DOWN) },
            modifier = Modifier.size(50.dp),
            shape = CircleShape
        ) {
            Text("⬇️", style = MaterialTheme.typography.bodyLarge)
        }
    }
}