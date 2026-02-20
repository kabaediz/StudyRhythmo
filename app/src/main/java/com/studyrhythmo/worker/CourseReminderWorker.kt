package com.studyrhythmo.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.studyrhythmo.MainActivity
import com.studyrhythmo.R
import com.studyrhythmo.StudyRhythmoApp

class CourseReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val courseId = inputData.getLong(KEY_COURSE_ID, -1L)
        val courseName = inputData.getString(KEY_COURSE_NAME) ?: return Result.failure()
        val courseRoom = inputData.getString(KEY_COURSE_ROOM) ?: ""

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, (courseId + 10000).toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, StudyRhythmoApp.CHANNEL_COURSES)
            .setSmallIcon(R.drawable.ic_schedule)
            .setContentTitle("Course Starting Soon")
            .setContentText("$courseName${if (courseRoom.isNotEmpty()) " in $courseRoom" else ""}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        applicationContext.getSystemService(NotificationManager::class.java)
            .notify((courseId + 10000).toInt(), notification)

        return Result.success()
    }

    companion object {
        const val KEY_COURSE_ID = "course_id"
        const val KEY_COURSE_NAME = "course_name"
        const val KEY_COURSE_ROOM = "course_room"
    }
}
