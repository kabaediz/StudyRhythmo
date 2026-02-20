package com.studyrhythmo.feature.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.NoteEntity
import com.studyrhythmo.data.repository.NoteRepository
import com.studyrhythmo.databinding.FragmentAddEditNoteBinding
import kotlinx.coroutines.launch

class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()
    private val args: AddEditNoteFragmentArgs by navArgs()
    private var editingNote: NoteEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.noteId > 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val repo = NoteRepository(AppDatabase.getInstance(requireContext()).noteDao())
                editingNote = repo.getNoteById(args.noteId)
                editingNote?.let {
                    binding.editNoteTitle.setText(it.title)
                    binding.editNoteContent.setText(it.content)
                }
            }
        }

        binding.btnSave.setOnClickListener { saveNote() }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
    }

    private fun saveNote() {
        val title = binding.editNoteTitle.text.toString().trim()
        if (title.isEmpty()) { binding.editNoteTitle.error = "Required"; return }
        val note = NoteEntity(
            id = editingNote?.id ?: 0,
            title = title,
            content = binding.editNoteContent.text.toString().trim(),
            createdAt = editingNote?.createdAt ?: System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        if (editingNote != null) viewModel.updateNote(note) else viewModel.insertNote(note)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
