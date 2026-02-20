package com.studyrhythmo.feature.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.studyrhythmo.R
import com.studyrhythmo.databinding.FragmentTasksBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TasksViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(
            onToggleComplete = { viewModel.toggleComplete(it) },
            onEdit = { task ->
                findNavController().navigate(TasksFragmentDirections.actionTasksToAddEditTask(task.id))
            },
            onDelete = { viewModel.deleteTask(it) }
        )
        binding.recyclerTasks.adapter = adapter

        binding.chipAll.setOnClickListener { viewModel.setFilter(TaskFilter.ALL) }
        binding.chipThisWeek.setOnClickListener { viewModel.setFilter(TaskFilter.THIS_WEEK) }
        binding.chipExams.setOnClickListener { viewModel.setFilter(TaskFilter.EXAMS) }
        binding.chipHighPriority.setOnClickListener { viewModel.setFilter(TaskFilter.HIGH_PRIORITY) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collectLatest { tasks ->
                adapter.submitList(tasks)
                binding.textNoTasks.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_tasks_to_addEditTask)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
