package com.studyrhythmo.feature.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.CourseEntity
import com.studyrhythmo.data.entity.StudySessionEntity
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.repository.CourseRepository
import com.studyrhythmo.data.repository.StudySessionRepository
import com.studyrhythmo.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import java.util.*

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val courseRepo = CourseRepository(db.courseDao())
    private val taskRepo = TaskRepository(db.taskDao())
    private val sessionRepo = StudySessionRepository(db.studySessionDao())

    val todayCourses: StateFlow<List<CourseEntity>> = run {
        val cal = Calendar.getInstance()
        val dow = cal.get(Calendar.DAY_OF_WEEK)
        // Calendar: Sunday=1, Monday=2 ... convert to 1=Monday..7=Sunday
        val day = if (dow == Calendar.SUNDAY) 7 else dow - 1
        courseRepo.getCoursesByDay(day)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    val upcomingTasks: StateFlow<List<TaskEntity>> = run {
        val start = System.currentTimeMillis()
        val end = start + 7L * 24 * 60 * 60 * 1000
        taskRepo.getTasksBetween(start, end)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    val todaySessions: StateFlow<List<StudySessionEntity>> = run {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val start = cal.timeInMillis
        val end = start + 24L * 60 * 60 * 1000
        sessionRepo.getSessionsBetween(start, end)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
}
