package com.studyrhythmo.data.repository

import com.studyrhythmo.data.dao.NoteDao
import com.studyrhythmo.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {
    fun getAllNotes(): Flow<List<NoteEntity>> = dao.getAllNotes()
    fun getNotesByCourse(courseId: Long): Flow<List<NoteEntity>> = dao.getNotesByCourse(courseId)
    fun searchNotes(query: String): Flow<List<NoteEntity>> = dao.searchNotes(query)
    suspend fun getNoteById(id: Long): NoteEntity? = dao.getNoteById(id)
    suspend fun insertNote(note: NoteEntity): Long = dao.insertNote(note)
    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)
    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)
}
