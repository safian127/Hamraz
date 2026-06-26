package com.example.domain.usecase

import com.example.domain.model.JournalEntry
import com.example.domain.repository.JournalRepository

class AddJournalEntryUseCase(private val repository: JournalRepository) {
    suspend operator fun invoke(entry: JournalEntry) {
        if (entry.title.isBlank() || entry.content.isBlank()) {
            throw IllegalArgumentException("Title and content cannot be empty")
        }
        repository.insertEntry(entry)
    }
}
