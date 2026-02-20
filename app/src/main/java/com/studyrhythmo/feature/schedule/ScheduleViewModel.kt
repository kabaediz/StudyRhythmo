package com.studyrhythmo.feature.schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.CourseEntity
import com.studyrhythmo.data.repository.CourseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ScheduleViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = CourseRepository(AppDatabase.getInstance(app).courseDao())

    val courses: StateFlow<List<CourseEntity>> =
        repo.getAllCourses().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    val coursesForDay: StateFlow<List<CourseEntity>> = _selectedDay.flatMapLatest { day ->
        repo.getCoursesByDay(day)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectDay(day: Int) { _selectedDay.value = day }

    fun insertCourse(course: CourseEntity) = viewModelScope.launch { repo.insertCourse(course) }
    fun updateCourse(course: CourseEntity) = viewModelScope.launch { repo.updateCourse(course) }
    fun deleteCourse(course: CourseEntity) = viewModelScope.launch { repo.deleteCourse(course) }
}
