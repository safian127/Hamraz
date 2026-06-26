package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRtl by viewModel.isRtl.collectAsState()
    val layoutDirection = if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    // Default mood choices with emojis
    val moods = listOf(
        "😊" to if (isRtl) "شاد" else "Happy",
        "🌸" to if (isRtl) "آرام" else "Calm",
        "🍂" to if (isRtl) "غمگین" else "Sad",
        "⚡" to if (isRtl) "مضطرب" else "Anxious",
        "💤" to if (isRtl) "خسته" else "Tired"
    )
    var selectedMoodIndex by remember { mutableIntStateOf(1) } // Default to "🌸 Calm"

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.add_entry),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = if (isRtl) "بازگشت" else "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = if (isRtl) "عنوان یادداشت" else "Title") },
                    placeholder = { Text(text = if (isRtl) "عنوان امروز شما..." else "Title of your day...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("journal_title_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                // Mood Selector Header
                Text(
                    text = if (isRtl) "احساس شما امروز چطور است؟" else "How do you feel today?",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Mood Selection Flow Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    moods.forEachIndexed { index, (emoji, name) ->
                        val isSelected = selectedMoodIndex == index
                        val containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                        val contentColor = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(containerColor)
                                .clickable { selectedMoodIndex = index }
                                .padding(vertical = 12.dp)
                        ) {
                            Text(text = emoji, fontSize = 24.sp)
                            Text(
                                text = name,
                                style = MaterialTheme.typography.labelSmall,
                                color = contentColor,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // Journal Content Input
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(text = if (isRtl) "متن یادداشت" else "Your thoughts") },
                    placeholder = { Text(text = if (isRtl) "بنویسید و هم راز خود را باز کنید..." else "Write down what's on your mind...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag("journal_content_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                // Save Button
                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            val selectedMood = moods[selectedMoodIndex].second
                            viewModel.addNewEntry(title, content, selectedMood)
                            onNavigateBack()
                        }
                    },
                    enabled = title.isNotBlank() && content.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("save_journal_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isRtl) "ذخیره همراز" else "Save to Hamraz",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
