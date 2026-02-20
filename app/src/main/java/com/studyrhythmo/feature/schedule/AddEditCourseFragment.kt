package com.studyrhythmo.feature.schedule

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.studyrhythmo.data.db.AppDatabase
import com.studyrhythmo.data.entity.CourseEntity
import com.studyrhythmo.data.repository.CourseRepository
import com.studyrhythmo.databinding.FragmentAddEditCourseBinding
import kotlinx.coroutines.launch

class AddEditCourseFragment : Fragment() {

    private var _binding: FragmentAddEditCourseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels()
    private val args: AddEditCourseFragmentArgs by navArgs()
    private var editingCourse: CourseEntity? = null
    private var startTime = "08:00"
    private var endTime = "09:30"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        binding.spinnerDay.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.btnStartTime.text = startTime
        binding.btnEndTime.text = endTime

        if (args.courseId > 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                val repo = CourseRepository(AppDatabase.getInstance(requireContext()).courseDao())
                editingCourse = repo.getCourseById(args.courseId)
                editingCourse?.let { populateFields(it) }
            }
        }

        binding.btnStartTime.setOnClickListener { showTimePicker(true) }
        binding.btnEndTime.setOnClickListener { showTimePicker(false) }
        binding.btnSave.setOnClickListener { saveCourse() }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
    }

    private fun populateFields(course: CourseEntity) {
        binding.editCourseName.setText(course.name)
        binding.editInstructor.setText(course.instructor)
        binding.editRoom.setText(course.room)
        binding.spinnerDay.setSelection(course.dayOfWeek - 1)
        startTime = course.startTime
        endTime = course.endTime
        binding.btnStartTime.text = startTime
        binding.btnEndTime.text = endTime
        binding.editReminderMinutes.setText(course.reminderMinutes.toString())
    }

    private fun showTimePicker(isStart: Boolean) {
        val parts = (if (isStart) startTime else endTime).split(":")
        TimePickerDialog(requireContext(), { _, h, m ->
            val t = "%02d:%02d".format(h, m)
            if (isStart) { startTime = t; binding.btnStartTime.text = t }
            else { endTime = t; binding.btnEndTime.text = t }
        }, parts[0].toInt(), parts[1].toInt(), true).show()
    }

    private fun saveCourse() {
        val name = binding.editCourseName.text.toString().trim()
        if (name.isEmpty()) { binding.editCourseName.error = "Required"; return }
        val reminder = binding.editReminderMinutes.text.toString().toIntOrNull() ?: 15
        val course = CourseEntity(
            id = editingCourse?.id ?: 0,
            name = name,
            instructor = binding.editInstructor.text.toString().trim(),
            room = binding.editRoom.text.toString().trim(),
            dayOfWeek = binding.spinnerDay.selectedItemPosition + 1,
            startTime = startTime,
            endTime = endTime,
            reminderMinutes = reminder
        )
        if (editingCourse != null) viewModel.updateCourse(course) else viewModel.insertCourse(course)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
