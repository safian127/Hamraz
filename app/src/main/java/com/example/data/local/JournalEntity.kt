package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.JournalEntry

@Entity(tableName = "journal_entries")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val mood: String,
    val timestamp: Long,
    val jalaliDate: String,
    val tagsString: String // Comma-separated tags e.g., "tag1,tag2"
) {
    fun toDomainModel(): JournalEntry {
        return JournalEntry(
            id = id,
            title = title,
            content = content,
            mood = mood,
            timestamp = timestamp,
            jalaliDate = jalaliDate,
            tags = if (tagsString.isBlank()) emptyList() else tagsString.split(",")
        )
    }

    companion object {
        fun fromDomainModel(domain: JournalEntry): JournalEntity {
            return JournalEntity(
                id = domain.id,
                title = domain.title,
                content = domain.content,
                mood = domain.mood,
                timestamp = domain.timestamp,
                jalaliDate = domain.jalaliDate,
                tagsString = domain.tags.joinToString(",")
            )
        }
    }
}
