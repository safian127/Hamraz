package com.example.domain.repository

import com.example.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllEntries(): Flow<List<JournalEntry>>
    fun getEntryById(id: Int): Flow<JournalEntry?>
    suspend fun insertEntry(entry: JournalEntry)
    suspend fun deleteEntry(entry: JournalEntry)
    suspend fun updateEntry(entry: JournalEntry)
}
