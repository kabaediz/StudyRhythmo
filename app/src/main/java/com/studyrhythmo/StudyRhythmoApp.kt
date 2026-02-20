package com.studyrhythmo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.Configuration

class StudyRhythmoApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val taskChannel = NotificationChannel(
            CHANNEL_TASKS,
            "Task Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { description = "Notifications for upcoming tasks and exams" }

        val courseChannel = NotificationChannel(
            CHANNEL_COURSES,
            "Course Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Notifications before courses start" }

        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(taskChannel)
        nm.createNotificationChannel(courseChannel)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setMinimumLoggingLevel(android.util.Log.INFO).build()

    companion object {
        const val CHANNEL_TASKS = "channel_tasks"
        const val CHANNEL_COURSES = "channel_courses"
    }
}
