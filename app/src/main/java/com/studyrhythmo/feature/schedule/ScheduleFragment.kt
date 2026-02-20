package com.studyrhythmo.feature.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.studyrhythmo.R
import com.studyrhythmo.databinding.FragmentScheduleBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CourseAdapter(
            onEdit = { course ->
                val action = ScheduleFragmentDirections.actionScheduleToAddEditCourse(course.id)
                findNavController().navigate(action)
            },
            onDelete = { course -> viewModel.deleteCourse(course) }
        )
        binding.recyclerCourses.adapter = adapter

        val dayButtons = listOf(
            binding.btnMon, binding.btnTue, binding.btnWed,
            binding.btnThu, binding.btnFri, binding.btnSat, binding.btnSun
        )
        dayButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.selectDay(index + 1)
                dayButtons.forEach { it.isSelected = false }
                button.isSelected = true
            }
        }
        // Default select Monday
        dayButtons[0].isSelected = true

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.coursesForDay.collectLatest { courses ->
                adapter.submitList(courses)
                binding.textNoCourses.visibility = if (courses.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        binding.fabAddCourse.setOnClickListener {
            findNavController().navigate(R.id.action_schedule_to_addEditCourse)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
