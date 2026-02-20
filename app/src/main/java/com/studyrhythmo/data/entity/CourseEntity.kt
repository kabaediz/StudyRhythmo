package com.studyrhythmo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val instructor: String = "",
    val room: String = "",
    val dayOfWeek: Int, // 1=Monday ... 7=Sunday
    val startTime: String, // "HH:mm"
    val endTime: String,   // "HH:mm"
    val color: Int = 0xFF6200EE.toInt(),
    val reminderMinutes: Int = 15
)
