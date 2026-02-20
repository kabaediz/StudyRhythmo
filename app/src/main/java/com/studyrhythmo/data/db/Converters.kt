package com.studyrhythmo.data.db

import androidx.room.TypeConverter
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.data.entity.TaskType

class Converters {
    @TypeConverter fun fromTaskPriority(value: TaskPriority): String = value.name
    @TypeConverter fun toTaskPriority(value: String): TaskPriority = TaskPriority.valueOf(value)
    @TypeConverter fun fromTaskType(value: TaskType): String = value.name
    @TypeConverter fun toTaskType(value: String): TaskType = TaskType.valueOf(value)
}
