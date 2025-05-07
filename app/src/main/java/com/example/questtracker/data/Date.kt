package com.example.questtracker.data

/**
 * Data class representing a date in the QuestTracker application.
 * This class is used to store date information for tasks and goals.
 *
 * @property month The month of the date (1-12)
 * @property day The day of the month (1-31)
 * @property year The year of the date (e.g., 2024)
 */
data class Date(
    val month: Int,
    val day: Int,
    val year: Int,
)