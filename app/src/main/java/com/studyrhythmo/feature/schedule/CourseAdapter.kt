package com.studyrhythmo.feature.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyrhythmo.data.entity.CourseEntity
import com.studyrhythmo.databinding.ItemCourseBinding

class CourseAdapter(
    private val onEdit: ((CourseEntity) -> Unit)? = null,
    private val onDelete: ((CourseEntity) -> Unit)? = null
) : ListAdapter<CourseEntity, CourseAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = getItem(position)
        with(holder.binding) {
            textCourseName.text = course.name
            textCourseInstructor.text = course.instructor
            textCourseRoom.text = course.room
            textCourseTime.text = "${course.startTime} - ${course.endTime}"
            viewColorIndicator.setBackgroundColor(course.color)
            btnEdit.setOnClickListener { onEdit?.invoke(course) }
            btnDelete.setOnClickListener { onDelete?.invoke(course) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<CourseEntity>() {
            override fun areItemsTheSame(a: CourseEntity, b: CourseEntity) = a.id == b.id
            override fun areContentsTheSame(a: CourseEntity, b: CourseEntity) = a == b
        }
    }
}
