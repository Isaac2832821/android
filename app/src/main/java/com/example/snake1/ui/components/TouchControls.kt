package com.example.snake1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.snake1.game.Direction
import kotlin.math.abs

@Composable
fun TouchControlArea(
    onDirectionChange: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    var dragStart by remember { mutableStateOf(Offset.Zero) }
    var dragEnd by remember { mutableStateOf(Offset.Zero) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStart = offset
                    },
                    onDragEnd = {
                        val deltaX = dragEnd.x - dragStart.x
                        val deltaY = dragEnd.y - dragStart.y
                        
                        // Determinar dirección basada en el gesto más largo
                        if (abs(deltaX) > abs(deltaY)) {
                            // Movimiento horizontal
                            if (deltaX > 50) {
                                onDirectionChange(Direction.RIGHT)
                            } else if (deltaX < -50) {
                                onDirectionChange(Direction.LEFT)
                            }
                        } else {
                            // Movimiento vertical
                            if (deltaY > 50) {
                                onDirectionChange(Direction.DOWN)
                            } else if (deltaY < -50) {
                                onDirectionChange(Direction.UP)
                            }
                        }
                    }
                ) { _, dragAmount ->
                    dragEnd += dragAmount
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎮 Área de Control Táctil",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Desliza en cualquier dirección",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}