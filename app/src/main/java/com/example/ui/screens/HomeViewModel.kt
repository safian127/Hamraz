package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.model.JournalEntry
import com.example.domain.usecase.AddJournalEntryUseCase
import com.example.domain.usecase.GetJournalEntriesUseCase
import com.example.util.JalaliCalendarHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getJournalEntriesUseCase: GetJournalEntriesUseCase,
    private val addJournalEntryUseCase: AddJournalEntryUseCase
) : ViewModel() {

    // Reactive flow of journal entries from SQLite
    val journalEntries: StateFlow<List<JournalEntry>> = getJournalEntriesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isRtl = MutableStateFlow(true) // Default to Persian RTL support
    val isRtl: StateFlow<Boolean> = _isRtl.asStateFlow()

    fun toggleLanguage() {
        _isRtl.value = !_isRtl.value
    }

    fun addNewEntry(title: String, content: String, mood: String) {
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()
            val jalaliDate = JalaliCalendarHelper.getFormattedJalaliDate(timestamp)
            val entry = JournalEntry(
                title = title,
                content = content,
                mood = mood,
                timestamp = timestamp,
                jalaliDate = jalaliDate
            )
            try {
                addJournalEntryUseCase(entry)
            } catch (e: Exception) {
                // Skeletons don't do heavy error handling yet, but ready for expansion
            }
        }
    }

    /**
     * Factory class to provide the required use cases to the ViewModel.
     */
    companion object {
        fun provideFactory(
            getJournalEntriesUseCase: GetJournalEntriesUseCase,
            addJournalEntryUseCase: AddJournalEntryUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(getJournalEntriesUseCase, addJournalEntryUseCase) as T
            }
        }
    }
}
