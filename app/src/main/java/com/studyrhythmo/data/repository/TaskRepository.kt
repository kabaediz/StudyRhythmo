package com.studyrhythmo.data.repository

import android.content.Context
import androidx.work.*
import com.studyrhythmo.data.dao.TaskDao
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.data.entity.TaskType
import com.studyrhythmo.worker.TaskReminderWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class TaskRepository(private val dao: TaskDao, private val context: Context) {
    fun getOpenTasks(): Flow<List<TaskEntity>> = dao.getOpenTasks()
    fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()
    fun getTasksBetween(start: Long, end: Long): Flow<List<TaskEntity>> = dao.getTasksBetween(start, end)
    fun getTasksByType(type: TaskType): Flow<List<TaskEntity>> = dao.getTasksByType(type)
    fun getTasksByPriority(priority: TaskPriority): Flow<List<TaskEntity>> = dao.getTasksByPriority(priority)
    suspend fun getTaskById(id: Long): TaskEntity? = dao.getTaskById(id)

    suspend fun insertTask(task: TaskEntity): Long {
        val id = dao.insertTask(task)
        scheduleNotification(task.copy(id = id))
        return id
    }

    suspend fun updateTask(task: TaskEntity) {
        dao.updateTask(task)
        if (task.isCompleted) {
            WorkManager.getInstance(context).cancelAllWorkByTag("task_${task.id}")
        } else {
            scheduleNotification(task)
        }
    }

    suspend fun deleteTask(task: TaskEntity) {
        dao.deleteTask(task)
        WorkManager.getInstance(context).cancelAllWorkByTag("task_${task.id}")
    }

    private fun scheduleNotification(task: TaskEntity) {
        if (task.isCompleted) return

        val delay = task.dueDate - System.currentTimeMillis()
        if (delay <= 0) return

        val data = workDataOf(
            TaskReminderWorker.KEY_TASK_ID to task.id,
            TaskReminderWorker.KEY_TASK_TITLE to task.title
        )

        val request = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("task_${task.id}")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "task_reminder_${task.id}",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
