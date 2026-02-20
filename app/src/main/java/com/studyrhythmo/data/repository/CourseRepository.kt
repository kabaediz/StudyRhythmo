package com.studyrhythmo.data.repository

import com.studyrhythmo.data.dao.CourseDao
import com.studyrhythmo.data.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val dao: CourseDao) {
    fun getAllCourses(): Flow<List<CourseEntity>> = dao.getAllCourses()
    fun getCoursesByDay(day: Int): Flow<List<CourseEntity>> = dao.getCoursesByDay(day)
    suspend fun getCourseById(id: Long): CourseEntity? = dao.getCourseById(id)
    suspend fun insertCourse(course: CourseEntity): Long = dao.insertCourse(course)
    suspend fun updateCourse(course: CourseEntity) = dao.updateCourse(course)
    suspend fun deleteCourse(course: CourseEntity) = dao.deleteCourse(course)
}
