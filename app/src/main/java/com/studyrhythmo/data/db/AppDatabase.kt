package com.studyrhythmo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyrhythmo.data.dao.CourseDao
import com.studyrhythmo.data.dao.NoteDao
import com.studyrhythmo.data.dao.StudySessionDao
import com.studyrhythmo.data.dao.TaskDao
import com.studyrhythmo.data.entity.CourseEntity
import com.studyrhythmo.data.entity.NoteEntity
import com.studyrhythmo.data.entity.StudySessionEntity
import com.studyrhythmo.data.entity.TaskEntity

@Database(
    entities = [CourseEntity::class, TaskEntity::class, StudySessionEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun taskDao(): TaskDao
    abstract fun studySessionDao(): StudySessionDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studyrhythmo.db"
                ).build().also { INSTANCE = it }
            }
    }
}
