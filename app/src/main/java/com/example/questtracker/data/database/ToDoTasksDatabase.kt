package com.example.questtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.questtracker.data.DAO.ToDoTaskDao
import com.example.questtracker.data.DateConverter
import com.example.questtracker.data.entity.ToDoTask

@Database(entities = [ToDoTask::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoTaskDao(): ToDoTaskDao
}
