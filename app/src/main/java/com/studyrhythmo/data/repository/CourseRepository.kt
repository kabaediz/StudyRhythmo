package com.studyrhythmo.data.repository

import android.content.Context
import androidx.work.*
import com.studyrhythmo.data.dao.CourseDao
import com.studyrhythmo.data.entity.CourseEntity
import com.studyrhythmo.worker.CourseReminderWorker
import kotlinx.coroutines.flow.Flow
import java.util.*
import java.util.concurrent.TimeUnit

class CourseRepository(private val dao: CourseDao, private val context: Context) {
    fun getAllCourses(): Flow<List<CourseEntity>> = dao.getAllCourses()
    fun getCoursesByDay(day: Int): Flow<List<CourseEntity>> = dao.getCoursesByDay(day)
    suspend fun getCourseById(id: Long): CourseEntity? = dao.getCourseById(id)

    suspend fun insertCourse(course: CourseEntity): Long {
        val id = dao.insertCourse(course)
        scheduleNotification(course.copy(id = id))
        return id
    }

    suspend fun updateCourse(course: CourseEntity) {
        dao.updateCourse(course)
        scheduleNotification(course)
    }

    suspend fun deleteCourse(course: CourseEntity) {
        dao.deleteCourse(course)
        WorkManager.getInstance(context).cancelAllWorkByTag("course_${course.id}")
    }

    private fun scheduleNotification(course: CourseEntity) {
        val calendar = Calendar.getInstance()
        val currentDay = if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) 7 else calendar.get(Calendar.DAY_OF_WEEK) - 1
        
        val timeParts = course.startTime.split(":")
        val startHour = timeParts[0].toInt()
        val startMinute = timeParts[1].toInt()

        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.MINUTE, -course.reminderMinutes)

        var daysUntil = course.dayOfWeek - currentDay
        if (daysUntil < 0 || (daysUntil == 0 && calendar.timeInMillis <= System.currentTimeMillis())) {
            daysUntil += 7
        }
        calendar.add(Calendar.DAY_OF_YEAR, daysUntil)

        val delay = calendar.timeInMillis - System.currentTimeMillis()
        if (delay <= 0) return

        val data = workDataOf(
            CourseReminderWorker.KEY_COURSE_ID to course.id,
            CourseReminderWorker.KEY_COURSE_NAME to course.name,
            CourseReminderWorker.KEY_COURSE_ROOM to course.room
        )

        val request = OneTimeWorkRequestBuilder<CourseReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("course_${course.id}")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "course_reminder_${course.id}",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
