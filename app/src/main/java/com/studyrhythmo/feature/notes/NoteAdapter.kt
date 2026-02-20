package com.studyrhythmo.feature.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyrhythmo.data.entity.NoteEntity
import com.studyrhythmo.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private val onEdit: ((NoteEntity) -> Unit)? = null,
    private val onDelete: ((NoteEntity) -> Unit)? = null
) : ListAdapter<NoteEntity, NoteAdapter.ViewHolder>(DIFF) {

    private val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    inner class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        with(holder.binding) {
            textNoteTitle.text = note.title
            textNotePreview.text = note.content.take(100)
            textNoteDate.text = sdf.format(Date(note.updatedAt))
            btnEdit.setOnClickListener { onEdit?.invoke(note) }
            btnDelete.setOnClickListener { onDelete?.invoke(note) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<NoteEntity>() {
            override fun areItemsTheSame(a: NoteEntity, b: NoteEntity) = a.id == b.id
            override fun areContentsTheSame(a: NoteEntity, b: NoteEntity) = a == b
        }
    }
}
