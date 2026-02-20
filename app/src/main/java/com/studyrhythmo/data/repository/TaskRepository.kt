package com.studyrhythmo.data.repository

import com.studyrhythmo.data.dao.TaskDao
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.data.entity.TaskType
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    fun getOpenTasks(): Flow<List<TaskEntity>> = dao.getOpenTasks()
    fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()
    fun getTasksBetween(start: Long, end: Long): Flow<List<TaskEntity>> = dao.getTasksBetween(start, end)
    fun getTasksByType(type: TaskType): Flow<List<TaskEntity>> = dao.getTasksByType(type)
    fun getTasksByPriority(priority: TaskPriority): Flow<List<TaskEntity>> = dao.getTasksByPriority(priority)
    suspend fun getTaskById(id: Long): TaskEntity? = dao.getTaskById(id)
    suspend fun insertTask(task: TaskEntity): Long = dao.insertTask(task)
    suspend fun updateTask(task: TaskEntity) = dao.updateTask(task)
    suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)
}
