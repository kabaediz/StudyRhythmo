package com.studyrhythmo.data.dao

import androidx.room.*
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.data.entity.TaskType
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY dueDate ASC, priority DESC")
    fun getOpenTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueDate BETWEEN :start AND :end AND isCompleted = 0 ORDER BY dueDate ASC")
    fun getTasksBetween(start: Long, end: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE type = :type AND isCompleted = 0 ORDER BY dueDate ASC")
    fun getTasksByType(type: TaskType): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE priority = :priority AND isCompleted = 0 ORDER BY dueDate ASC")
    fun getTasksByPriority(priority: TaskPriority): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
