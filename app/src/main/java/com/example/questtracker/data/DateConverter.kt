package com.example.questtracker.data

import androidx.room.TypeConverter

/**
 * Type converter class for converting between Date objects and their string representation.
 * This class is used by Room to store Date objects in the database as strings.
 */
class DateConverter {
    /**
     * Converts a Date object to a string representation.
     * The format is "MM-DD-YYYY".
     *
     * @param date The Date object to convert
     * @return A string representation of the date
     */
    @TypeConverter
    fun fromDate(date: Date): String {
        return "${date.month}-${date.day}-${date.year}"
    }

    /**
     * Converts a string representation back to a Date object.
     * The expected format is "MM-DD-YYYY".
     *
     * @param dateString The string representation of the date
     * @return A Date object created from the string
     */
    @TypeConverter
    fun toDate(dateString: String): Date {
        val parts = dateString.split("-")
        return Date(
            month = parts[0].toInt(),
            day = parts[1].toInt(),
            year = parts[2].toInt()
        )
    }
}
