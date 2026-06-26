package com.example.domain.usecase

import com.example.domain.model.JournalEntry
import com.example.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow

class GetJournalEntriesUseCase(private val repository: JournalRepository) {
    operator fun invoke(): Flow<List<JournalEntry>> = repository.getAllEntries()
}
