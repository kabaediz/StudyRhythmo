package com.studyrhythmo.data.repository

import com.studyrhythmo.data.dao.StudySessionDao
import com.studyrhythmo.data.entity.StudySessionEntity
import kotlinx.coroutines.flow.Flow

class StudySessionRepository(private val dao: StudySessionDao) {
    fun getAllSessions(): Flow<List<StudySessionEntity>> = dao.getAllSessions()
    fun getSessionsBetween(start: Long, end: Long): Flow<List<StudySessionEntity>> = dao.getSessionsBetween(start, end)
    fun getSessionsByCourse(courseId: Long): Flow<List<StudySessionEntity>> = dao.getSessionsByCourse(courseId)
    suspend fun getSessionById(id: Long): StudySessionEntity? = dao.getSessionById(id)
    suspend fun insertSession(session: StudySessionEntity): Long = dao.insertSession(session)
    suspend fun updateSession(session: StudySessionEntity) = dao.updateSession(session)
    suspend fun deleteSession(session: StudySessionEntity) = dao.deleteSession(session)
}
