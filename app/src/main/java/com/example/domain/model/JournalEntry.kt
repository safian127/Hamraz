package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class JournalEntry(
    val id: Int = 0,
    val title: String,
    val content: String,
    val mood: String, // e.g., "HAPPY", "CALM", "SAD", "ANXIOUS", "TIRED"
    val timestamp: Long = System.currentTimeMillis(),
    val jalaliDate: String, // Pre-formatted Jalali date string e.g., "۱۴۰۵/۰۴/۰۵"
    val tags: List<String> = emptyList()
)
