package com.studyrhythmo.feature.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.NoteEntity
import com.studyrhythmo.data.repository.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = NoteRepository(AppDatabase.getInstance(app).noteDao())

    private val _searchQuery = MutableStateFlow("")

    val notes: StateFlow<List<NoteEntity>> = _searchQuery.flatMapLatest { q ->
        if (q.isEmpty()) repo.getAllNotes() else repo.searchNotes(q)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearchQuery(q: String) { _searchQuery.value = q }
    fun insertNote(note: NoteEntity) = viewModelScope.launch { repo.insertNote(note) }
    fun updateNote(note: NoteEntity) = viewModelScope.launch { repo.updateNote(note) }
    fun deleteNote(note: NoteEntity) = viewModelScope.launch { repo.deleteNote(note) }
}
