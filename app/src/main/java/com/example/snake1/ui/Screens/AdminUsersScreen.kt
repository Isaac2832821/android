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
import com.example.snake1.data.models.User
import com.example.snake1.data.models.UserRole
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersScreen(
    navController: NavController,
    userRepository: SimpleUserRepository
) {
    val users by userRepository.users.collectAsState()
    val currentUser by userRepository.currentUser.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<User?>(null) }
    var showEditDialog by remember { mutableStateOf<User?>(null) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Dialog para eliminar usuario
    showDeleteDialog?.let { user ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Eliminar Usuario") },
            text = { Text("¿Estás seguro de que quieres eliminar a ${user.username}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        userRepository.deleteUser(user.id)
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

    // Dialog para editar usuario
    showEditDialog?.let { user ->
        var editedUsername by remember { mutableStateOf(user.username) }
        var editedEmail by remember { mutableStateOf(user.email) }
        var editedRole by remember { mutableStateOf(user.role) }

        AlertDialog(
            onDismissRequest = { showEditDialog = null },
            title = { Text("Editar Usuario") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedUsername,
                        onValueChange = { editedUsername = it },
                        label = { Text("Nombre de usuario") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedEmail,
                        onValueChange = { editedEmail = it },
                        label = { Text("Email") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Rol:", style = MaterialTheme.typography.bodyMedium)
                    Row {
                        RadioButton(
                            selected = editedRole == UserRole.PLAYER,
                            onClick = { editedRole = UserRole.PLAYER }
                        )
                        Text("Jugador", modifier = Modifier.padding(start = 8.dp))
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        RadioButton(
                            selected = editedRole == UserRole.ADMIN,
                            onClick = { editedRole = UserRole.ADMIN }
                        )
                        Text("Admin", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val updatedUser = user.copy(
                            username = editedUsername,
                            email = editedEmail,
                            role = editedRole
                        )
                        userRepository.updateUser(updatedUser)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("👥 Gestión de Usuarios") },
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
                        Text("Total de usuarios: ${users.size}")
                        Text("Administradores: ${users.count { it.role == UserRole.ADMIN }}")
                        Text("Jugadores: ${users.count { it.role == UserRole.PLAYER }}")
                    }
                }
            }

            items(users) { user ->
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
                                    text = user.username,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Registrado: ${dateFormat.format(Date(user.createdAt))}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Puntuación: ${user.totalScore} | Partidas: ${user.gamesPlayed}",
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
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showEditDialog = user },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("✏️ Editar")
                            }
                            
                            // No permitir eliminar al usuario actual
                            if (user.id != currentUser?.id) {
                                OutlinedButton(
                                    onClick = { showDeleteDialog = user },
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
}