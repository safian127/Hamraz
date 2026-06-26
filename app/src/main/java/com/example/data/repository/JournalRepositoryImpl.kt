package com.example.data.repository

import com.example.data.local.JournalDao
import com.example.data.local.JournalEntity
import com.example.domain.model.JournalEntry
import com.example.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JournalRepositoryImpl(
    private val dao: JournalDao
) : JournalRepository {

    override fun getAllEntries(): Flow<List<JournalEntry>> {
        return dao.getAllEntries().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getEntryById(id: Int): Flow<JournalEntry?> {
        return dao.getEntryById(id).map { entity ->
            entity?.toDomainModel()
        }
    }

    override suspend fun insertEntry(entry: JournalEntry) {
        val entity = JournalEntity.fromDomainModel(entry)
        val finalEntity = if (entry.id == 0) entity.copy(id = 0) else entity
        dao.insertEntry(finalEntity)
    }

    override suspend fun deleteEntry(entry: JournalEntry) {
        dao.deleteEntry(JournalEntity.fromDomainModel(entry))
    }

    override suspend fun updateEntry(entry: JournalEntry) {
        dao.updateEntry(JournalEntity.fromDomainModel(entry))
    }
}
