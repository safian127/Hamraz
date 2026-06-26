package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HomeViewModel
import com.example.ui.screens.JournalScreen
import com.example.ui.screens.SettingsScreen

@Composable
fun HamrazNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = modifier
    ) {
        composable<Screen.Home> {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToJournal = { navController.navigate(Screen.Journal) },
                onNavigateToSettings = { navController.navigate(Screen.Settings) }
            )
        }
        composable<Screen.Journal> {
            JournalScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Settings> {
            SettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
