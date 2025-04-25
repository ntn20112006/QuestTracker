package com.example.questtracker.data

data class Goal(
    val id: Int,
    var title: String,
    var description: String,
    var target: Int,
    val creationDate: Date,
    val deadline: Date,
    var isComplete: Boolean,
    var toDoTaskLink: ToDoTask?
)
