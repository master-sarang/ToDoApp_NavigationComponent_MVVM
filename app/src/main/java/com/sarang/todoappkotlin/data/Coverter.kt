package com.sarang.todoappkotlin.data

import androidx.room.TypeConverter
import com.sarang.todoappkotlin.data.models.Priority

class Coverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priorityString: String) : Priority {
        return Priority.valueOf(priorityString)
    }
}