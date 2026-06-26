package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.di.HamrazApplication
import com.example.ui.navigation.HamrazNavGraph
import com.example.ui.screens.HomeViewModel
import com.example.ui.theme.HamrazTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Retrieve Dependency Injection container from HamrazApplication
        val appContainer = (application as HamrazApplication).container

        // Instantiate ViewModel using custom MVVM factory (Constructor Injection)
        val viewModel: HomeViewModel by viewModels {
            HomeViewModel.provideFactory(
                getJournalEntriesUseCase = appContainer.getJournalEntriesUseCase,
                addJournalEntryUseCase = appContainer.addJournalEntryUseCase
            )
        }

        // Enable edge-to-edge full screen drawing
        enableEdgeToEdge()

        setContent {
            HamrazTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HamrazNavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
