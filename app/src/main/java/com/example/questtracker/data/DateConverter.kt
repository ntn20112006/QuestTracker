package com.example.questtracker.data

import androidx.room.TypeConverter

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date): String = "${date.day}-${date.month}-${date.year}"

    @TypeConverter
    fun toDate(value: String): Date {
        val parts = value.split("-")
        return Date(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }
}
