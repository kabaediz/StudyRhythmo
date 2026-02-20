package com.studyrhythmo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_sessions")
data class StudySessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseId: Long? = null,
    val topic: String = "",
    val plannedStart: Long, // epoch millis
    val plannedDurationMinutes: Int,
    val actualDurationMinutes: Int = 0,
    val isCompleted: Boolean = false
)
