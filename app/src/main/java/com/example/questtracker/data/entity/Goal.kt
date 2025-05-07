package com.example.questtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.questtracker.data.Date

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
    val deadline: Date,
    var isComplete: Boolean
)
