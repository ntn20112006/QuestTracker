package com.example.questtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.questtracker.data.Date

/**
 * Entity class representing a task in the QuestTracker application.
 * This class is used to store task information in the Room database.
 *
 * @property id Unique identifier for the task, auto-generated
 * @property title The title or name of the task
 * @property description Detailed description of the task
 * @property isRecurring Whether the task repeats at regular intervals
 * @property recurrenceInterval Number of days between task recurrences (if recurring)
 * @property deadline The date by which the task should be completed
 * @property isComplete Whether the task has been completed
 */
@Entity(tableName = "todoTasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
    var isRecurring: Boolean,
    var recurrenceInterval: Int, // days
    val deadline: Date,
    var isComplete: Boolean,
)
