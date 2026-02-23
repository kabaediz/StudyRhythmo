package com.studyrhythmo.feature.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyrhythmo.R
import com.studyrhythmo.data.entity.TaskEntity
import com.studyrhythmo.data.entity.TaskPriority
import com.studyrhythmo.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val onToggleComplete: (TaskEntity) -> Unit,
    private val onEdit: ((TaskEntity) -> Unit)? = null,
    private val onDelete: ((TaskEntity) -> Unit)? = null
) : ListAdapter<TaskEntity, TaskAdapter.ViewHolder>(DIFF) {

    private val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        with(holder.binding) {
            textTaskTitle.text = task.title
            textTaskDueDate.text = sdf.format(Date(task.dueDate))
            textTaskType.text = task.type.name
            chipPriority.text = task.priority.name
            chipPriority.setChipBackgroundColorResource(
                when (task.priority) {
                    TaskPriority.HIGH -> R.color.priority_high_container
                    TaskPriority.MEDIUM -> R.color.priority_medium_container
                    TaskPriority.LOW -> R.color.priority_low_container
                }
            )
            checkboxComplete.isChecked = task.isCompleted
            if (task.isCompleted) {
                textTaskTitle.paintFlags = textTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textTaskTitle.paintFlags = textTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            checkboxComplete.setOnClickListener { onToggleComplete(task) }
            btnEdit.setOnClickListener { onEdit?.invoke(task) }
            btnDelete.setOnClickListener { onDelete?.invoke(task) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(a: TaskEntity, b: TaskEntity) = a.id == b.id
            override fun areContentsTheSame(a: TaskEntity, b: TaskEntity) = a == b
        }
    }
}
