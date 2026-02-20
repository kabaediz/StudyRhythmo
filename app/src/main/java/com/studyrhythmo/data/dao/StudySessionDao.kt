package com.studyrhythmo.data.dao

import androidx.room.*
import com.studyrhythmo.data.entity.StudySessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {
    @Query("SELECT * FROM study_sessions ORDER BY plannedStart ASC")
    fun getAllSessions(): Flow<List<StudySessionEntity>>

    @Query("SELECT * FROM study_sessions WHERE plannedStart BETWEEN :start AND :end ORDER BY plannedStart ASC")
    fun getSessionsBetween(start: Long, end: Long): Flow<List<StudySessionEntity>>

    @Query("SELECT * FROM study_sessions WHERE courseId = :courseId ORDER BY plannedStart DESC")
    fun getSessionsByCourse(courseId: Long): Flow<List<StudySessionEntity>>

    @Query("SELECT * FROM study_sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): StudySessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: StudySessionEntity): Long

    @Update
    suspend fun updateSession(session: StudySessionEntity)

    @Delete
    suspend fun deleteSession(session: StudySessionEntity)
}
