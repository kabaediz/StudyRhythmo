package com.studyrhythmo.feature.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyrhythmo.data.entity.StudySessionEntity
import com.studyrhythmo.databinding.ItemStudySessionBinding
import java.text.SimpleDateFormat
import java.util.*

class StudySessionAdapter(
    private val onStart: ((StudySessionEntity) -> Unit)? = null,
    private val onDelete: ((StudySessionEntity) -> Unit)? = null
) : ListAdapter<StudySessionEntity, StudySessionAdapter.ViewHolder>(DIFF) {

    private val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())

    inner class ViewHolder(val binding: ItemStudySessionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemStudySessionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = getItem(position)
        with(holder.binding) {
            textSessionTopic.text = session.topic.ifEmpty { "Study Session" }
            textSessionTime.text = sdf.format(Date(session.plannedStart))
            textSessionDuration.text = "${session.plannedDurationMinutes} min"
            textActualDuration.text = if (session.isCompleted) "Done: ${session.actualDurationMinutes} min" else "Planned"
            btnStartSession.setOnClickListener { onStart?.invoke(session) }
            btnDeleteSession.setOnClickListener { onDelete?.invoke(session) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<StudySessionEntity>() {
            override fun areItemsTheSame(a: StudySessionEntity, b: StudySessionEntity) = a.id == b.id
            override fun areContentsTheSame(a: StudySessionEntity, b: StudySessionEntity) = a == b
        }
    }
}
