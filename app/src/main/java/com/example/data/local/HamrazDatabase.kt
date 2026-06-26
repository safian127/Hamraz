package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [JournalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HamrazDatabase : RoomDatabase() {
    abstract val journalDao: JournalDao

    companion object {
        const val DATABASE_NAME = "hamraz_db"
    }
}
