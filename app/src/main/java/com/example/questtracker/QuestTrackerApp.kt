package com.example.questtracker

import android.app.Application
import androidx.room.Room
import com.example.questtracker.data.database.AppDatabase

class QuestTrackerApp : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "questtracker-db"
        ).fallbackToDestructiveMigration(true).build()
    }
}
