package com.example.questtracker

import android.app.Application
import androidx.room.Room
import com.example.questtracker.data.database.AppDatabase

/**
 * Main application class for the QuestTracker application.
 * This class initializes the Room database and provides access to it throughout the app.
 */
class QuestTrackerApp : Application() {
    /**
     * The Room database instance for the application.
     * This is initialized in onCreate() and can be accessed throughout the app.
     */
    lateinit var database: AppDatabase
        private set

    /**
     * Called when the application is first created.
     * Initializes the Room database with the application context.
     */
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "quest_tracker_database"
        ).build()
    }
}
