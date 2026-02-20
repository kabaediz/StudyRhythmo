package com.studyrhythmo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TaskPriority { LOW, MEDIUM, HIGH }
enum class TaskType { TASK, PROJECT, EXAM }

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val courseId: Long? = null,
    val dueDate: Long, // epoch millis
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val type: TaskType = TaskType.TASK,
    val isCompleted: Boolean = false
)
