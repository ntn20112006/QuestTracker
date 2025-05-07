package com.example.questtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.questtracker.data.DAO.GoalDao
import com.example.questtracker.data.DAO.ToDoTaskDao
import com.example.questtracker.data.DateConverter
import com.example.questtracker.data.entity.Goal
import com.example.questtracker.data.entity.ToDoTask

/**
 * Main database class for the QuestTracker application.
 * This class defines the database configuration and provides access to the DAOs.
 *
 * The database contains two tables:
 * - todoTasks: Stores user tasks with their properties
 * - goals: Stores user goals with their properties
 *
 * @property version The current database version (2)
 * @property exportSchema Whether to export the database schema (disabled)
 */
@Database(entities = [ToDoTask::class, Goal::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to the ToDoTaskDao for managing tasks.
     *
     * @return An instance of ToDoTaskDao
     */
    abstract fun toDoTaskDao(): ToDoTaskDao

    /**
     * Provides access to the GoalDao for managing goals.
     *
     * @return An instance of GoalDao
     */
    abstract fun goalDao(): GoalDao
}
