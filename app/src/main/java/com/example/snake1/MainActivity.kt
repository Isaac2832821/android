package com.example.snake1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// ⬇️ CORRECCIÓN DE LA IMPORTACIÓN ⬇️
// (Debe importar la función real del tema, que es Snake1Theme, no la clase de anotación Theme)
import com.example.snake1.ui.theme.Snake1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ⬇️ CORRECCIÓN DE USO: Usa el nombre correcto del tema ⬇️
            Snake1Theme {
                SnakeApp() //
            }
        }
    }
}

// ... La función que define el contenido principal (que moveremos/renombraremos) ...