# 🐍 Snake Game Android

Una implementación moderna del clásico juego Snake para Android, desarrollada con **Jetpack Compose** y **Kotlin**.

## 📱 Características

### 🎮 Juego Principal
- **Juego Snake completo** con mecánicas clásicas
- **Controles táctiles** y **botones direccionales**
- **Sistema de puntuación** en tiempo real
- **Vibración háptica** para feedback inmersivo
- **Animaciones fluidas** y efectos visuales

### 👤 Sistema de Usuarios
- **Registro e inicio de sesión** de usuarios
- **Perfiles personalizados** con estadísticas
- **Seguimiento de puntuaciones** individuales
- **Rankings globales** de jugadores

### 🛠️ Panel de Administración
- **Gestión de usuarios** (crear, editar, eliminar)
- **Administración de juegos** y configuraciones
- **Control de puntuaciones** y estadísticas
- **Vista completa del sistema**

### 🎨 Interfaz Moderna
- **Material Design 3** con tema dinámico
- **Componentes animados** y transiciones suaves
- **Diseño responsivo** para diferentes pantallas
- **Iconos y emojis** para mejor UX

## 🏗️ Arquitectura

### 📦 Estructura del Proyecto
```
app/src/main/java/com/example/snake1/
├── data/
│   ├── models/          # Modelos de datos (User, Score, Game)
│   └── repository/      # Repositorios para manejo de datos
├── game/
│   ├── SnakeGameState.kt      # Estado del juego
│   ├── SnakeGameViewModel.kt  # Lógica del juego
│   └── Direction.kt           # Direcciones de movimiento
├── navigation/
│   └── NavGraph.kt      # Navegación de la app
├── ui/
│   ├── components/      # Componentes reutilizables
│   └── Screens/         # Pantallas de la aplicación
└── utils/
    └── HapticFeedback.kt # Utilidades de vibración
```

### 🔧 Tecnologías Utilizadas
- **Kotlin** - Lenguaje principal
- **Jetpack Compose** - UI moderna y declarativa
- **Material Design 3** - Sistema de diseño
- **Navigation Compose** - Navegación entre pantallas
- **StateFlow** - Manejo de estado reactivo
- **Coroutines** - Programación asíncrona

## 🚀 Instalación

### Prerrequisitos
- Android Studio Arctic Fox o superior
- SDK de Android 21+ (Android 5.0)
- Kotlin 1.8+

### Pasos de Instalación
1. **Clona el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/snake-game-android.git
   cd snake-game-android
   ```

2. **Abre en Android Studio**
   - Abre Android Studio
   - Selecciona "Open an existing project"
   - Navega a la carpeta del proyecto

3. **Sincroniza el proyecto**
   - Android Studio sincronizará automáticamente las dependencias
   - Espera a que termine la sincronización

4. **Ejecuta la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Presiona el botón "Run" o usa `Shift + F10`

## 🎯 Cómo Jugar

### 🕹️ Controles
- **Gestos táctiles**: Desliza en cualquier dirección
- **Botones direccionales**: Usa los botones ⬆️⬇️⬅️➡️
- **Pausa**: Toca el botón de pausa durante el juego

### 🏆 Objetivo
- Controla la serpiente para comer la comida (🔴)
- Evita chocar con las paredes o contigo mismo
- Consigue la puntuación más alta posible
- Compite en los rankings globales

## 📸 Capturas de Pantalla

### 🎮 Pantalla de Juego
- Tablero de juego con serpiente animada
- Controles táctiles y botones
- Puntuación en tiempo real

### 👤 Sistema de Usuarios
- Login y registro intuitivos
- Perfiles con estadísticas personales
- Rankings de mejores jugadores

### 🛠️ Panel Admin
- Gestión completa de usuarios
- Control de puntuaciones
- Administración del sistema

## 🔧 Configuración de Desarrollo

### 📋 Dependencias Principales
```kotlin
// Jetpack Compose
implementation "androidx.compose.ui:ui:$compose_version"
implementation "androidx.compose.material3:material3:$material3_version"
implementation "androidx.activity:activity-compose:$activity_compose_version"

// Navigation
implementation "androidx.navigation:navigation-compose:$nav_version"

// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
```

### 🎨 Personalización
- **Colores**: Modifica `ui/theme/Color.kt`
- **Tipografía**: Edita `ui/theme/Type.kt`
- **Dimensiones**: Ajusta valores en `ui/theme/Dimension.kt`

## 🤝 Contribuir

### 📝 Cómo Contribuir
1. **Fork** el proyecto
2. **Crea** una rama para tu feature (`git checkout -b feature/nueva-caracteristica`)
3. **Commit** tus cambios (`git commit -m 'Agrega nueva característica'`)
4. **Push** a la rama (`git push origin feature/nueva-caracteristica`)
5. **Abre** un Pull Request

### 🐛 Reportar Bugs
- Usa las **Issues** de GitHub
- Incluye pasos para reproducir el bug
- Agrega capturas de pantalla si es posible

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 📊 Estado del Proyecto

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Version](https://img.shields.io/badge/version-1.0.0-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Platform](https://img.shields.io/badge/platform-Android-lightgrey)

---

⭐ **¡Dale una estrella si te gusta el proyecto!** ⭐
