package com.studyrhythmo.feature.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.data.entity.TaskType
import com.studyrhythmo.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class TaskFilter { ALL, THIS_WEEK, EXAMS, HIGH_PRIORITY }

class TasksViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = TaskRepository(AppDatabase.getInstance(app).taskDao())

    private val _filter = MutableStateFlow(TaskFilter.ALL)
    val filter: StateFlow<TaskFilter> = _filter.asStateFlow()

    val tasks: StateFlow<List<TaskEntity>> = _filter.flatMapLatest { f ->
        when (f) {
            TaskFilter.ALL -> repo.getOpenTasks()
            TaskFilter.THIS_WEEK -> {
                val start = System.currentTimeMillis()
                val end = start + 7L * 24 * 60 * 60 * 1000
                repo.getTasksBetween(start, end)
            }
            TaskFilter.EXAMS -> repo.getTasksByType(TaskType.EXAM)
            TaskFilter.HIGH_PRIORITY -> repo.getTasksByPriority(TaskPriority.HIGH)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setFilter(f: TaskFilter) { _filter.value = f }
    fun insertTask(task: TaskEntity) = viewModelScope.launch { repo.insertTask(task) }
    fun updateTask(task: TaskEntity) = viewModelScope.launch { repo.updateTask(task) }
    fun deleteTask(task: TaskEntity) = viewModelScope.launch { repo.deleteTask(task) }
    fun toggleComplete(task: TaskEntity) = viewModelScope.launch { repo.updateTask(task.copy(isCompleted = !task.isCompleted)) }
}
