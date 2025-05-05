package com.example.questtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.questtracker.data.Date

@Entity(tableName = "todoTasks")
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
    var isRecurring: Boolean,
    var recurrenceInterval: Int, // days
    val deadline: Date,
    var isComplete: Boolean,
)
