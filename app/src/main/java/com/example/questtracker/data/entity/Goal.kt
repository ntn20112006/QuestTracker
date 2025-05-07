package com.example.questtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.questtracker.data.Date

/**
 * Entity class representing a goal in the QuestTracker application.
 * This class is used to store goal information in the Room database.
 *
 * @property id Unique identifier for the goal, auto-generated
 * @property title The title or name of the goal
 * @property description Detailed description of the goal
 * @property deadline The target date for completing the goal
 * @property isComplete Whether the goal has been achieved
 */
@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
    val deadline: Date,
    var isComplete: Boolean
)
