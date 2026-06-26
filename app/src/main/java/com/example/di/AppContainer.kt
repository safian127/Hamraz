package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.HamrazDatabase
import com.example.data.repository.JournalRepositoryImpl
import com.example.domain.repository.JournalRepository
import com.example.domain.usecase.AddJournalEntryUseCase
import com.example.domain.usecase.GetJournalEntriesUseCase

/**
 * Dependency Injection container at the application level.
 * This holds single instances of the Database, Repository, and Use Cases,
 * ensuring clean separation of concerns and fast compile times.
 */
interface AppContainer {
    val journalRepository: JournalRepository
    val getJournalEntriesUseCase: GetJournalEntriesUseCase
    val addJournalEntryUseCase: AddJournalEntryUseCase
}

class AppContainerImpl(private val context: Context) : AppContainer {

    // Lazy initialization of the database to ensure it's created only when needed on a background worker thread
    private val database: HamrazDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            HamrazDatabase::class.java,
            HamrazDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration() // Simple migration strategy for development
        .build()
    }

    override val journalRepository: JournalRepository by lazy {
        JournalRepositoryImpl(database.journalDao)
    }

    override val getJournalEntriesUseCase: GetJournalEntriesUseCase by lazy {
        GetJournalEntriesUseCase(journalRepository)
    }

    override val addJournalEntryUseCase: AddJournalEntryUseCase by lazy {
        AddJournalEntryUseCase(journalRepository)
    }
}
