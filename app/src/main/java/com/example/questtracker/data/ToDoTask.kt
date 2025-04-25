package com.example.questtracker.data

data class ToDoTask(
    val id: Int,
    var title: String,
    var description: String,
    var isRecurring: Boolean,
    var recurrenceInterval: Int, // days
    val deadline: Date,
    var isComplete: Boolean,
    var goalLink: Goal?
)
