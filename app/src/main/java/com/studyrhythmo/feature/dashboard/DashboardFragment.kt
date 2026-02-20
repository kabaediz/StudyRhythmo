package com.studyrhythmo.feature.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.studyrhythmo.databinding.FragmentDashboardBinding
import com.studyrhythmo.feature.schedule.CourseAdapter
import com.studyrhythmo.feature.study.StudySessionAdapter
import com.studyrhythmo.feature.tasks.TaskAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var courseAdapter: CourseAdapter
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var sessionAdapter: StudySessionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        binding.textDate.text = sdf.format(Date())

        courseAdapter = CourseAdapter()
        taskAdapter = TaskAdapter(onToggleComplete = {})
        sessionAdapter = StudySessionAdapter()

        binding.recyclerTodayCourses.adapter = courseAdapter
        binding.recyclerUpcomingTasks.adapter = taskAdapter
        binding.recyclerTodaySessions.adapter = sessionAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todayCourses.collectLatest { courses ->
                courseAdapter.submitList(courses)
                binding.textNoCourses.visibility = if (courses.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.upcomingTasks.collectLatest { tasks ->
                taskAdapter.submitList(tasks)
                binding.textNoTasks.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todaySessions.collectLatest { sessions ->
                sessionAdapter.submitList(sessions)
                binding.textNoSessions.visibility = if (sessions.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
