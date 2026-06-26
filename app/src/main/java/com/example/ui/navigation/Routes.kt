package com.example.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object Journal : Screen

    @Serializable
    data object Settings : Screen
}
